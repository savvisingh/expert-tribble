package com.example.deliveryherotest.repository.api.helper

import androidx.annotation.MainThread
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.Flowable

abstract class DbBoundResource<ResultType>
@MainThread constructor(private val appScheduler: ISchedulers){

    private lateinit var result: Flowable<Resource<ResultType>>

    init {
        val diskObservable = Flowable.defer {
            loadFromDb()
                // Read from disk on Computation Scheduler
                .subscribeOn(appScheduler.computation())
        }

        result = diskObservable
            .map<Resource<ResultType>> { Resource.Success(it) }
            .onErrorReturn { Resource.Error(it.message) }
            // Read results in Android Main Thread (UI)
            .observeOn(appScheduler.io())
            .startWith(Resource.Loading())
    }

    fun asFlowable(): Flowable<Resource<ResultType>> {
        return result
    }

    protected abstract fun loadFromDb(): Flowable<ResultType>
}