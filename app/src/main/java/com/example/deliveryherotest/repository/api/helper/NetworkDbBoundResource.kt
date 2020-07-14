package com.example.deliveryherotest.repository.api.helper

import androidx.annotation.MainThread
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.Flowable
import org.json.JSONObject
import retrofit2.Response

abstract class NetworkDbBoundResource<ResultType, RequestType>
@MainThread constructor(private val appScheduler: ISchedulers) {

    private lateinit var result: Flowable<Resource<ResultType>>

    init {
        // Lazy disk observable.
        val diskObservable = Flowable.defer {
            loadFromDb()
                // Read from disk on Computation Scheduler
                .subscribeOn(appScheduler.computation())
        }

        // Lazy network observable.
        val networkObservable = Flowable.defer {
            createCall()
                // Request API on IO Scheduler
                .subscribeOn(appScheduler.io())
                // Read/Write to disk on Computation Scheduler
                .observeOn(appScheduler.computation())
                .doOnNext { request: Response<RequestType> ->
                    try {
                        saveCallResult(processResponse(request))
                    } catch (e: Throwable) { //TODO throw error from api object
                        val jObjError = JSONObject(request.errorBody()?.string())
                        throw Exception(jObjError.getString("data"))
                    }
                }
                .flatMap { loadFromDb() }
        }


            result = if (shouldFetch()) {
                networkObservable
                    .map<Resource<ResultType>> { Resource.Success(it) }
                    .onErrorReturn { Resource.Error(it.message) }
                    // Read results in Android Main Thread (UI)
                    .observeOn(appScheduler.ui())
                    .startWith(Resource.Loading())
            } else {
                diskObservable
                    .map<Resource<ResultType>> { Resource.Success(it) }
                    .onErrorReturn { Resource.Error(it.message) }
                    // Read results in Android Main Thread (UI)
                    .observeOn(appScheduler.ui())
                    .startWith(Resource.Loading())
            }

    }

    fun asFlowable(): Flowable<Resource<ResultType>> {
        return result
    }

    private fun processResponse(response: Response<RequestType>): RequestType {
        return response.body()!!
    }

    protected abstract fun saveCallResult(request: RequestType)

    protected abstract fun loadFromDb(): Flowable<ResultType>

    protected abstract fun shouldFetch(): Boolean

    protected abstract fun createCall(): Flowable<Response<RequestType>>
}