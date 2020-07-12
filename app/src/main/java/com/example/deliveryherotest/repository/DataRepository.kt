package com.example.deliveryherotest.repository

import android.content.SharedPreferences
import com.example.deliveryherotest.repository.db.InAppDataBase
import com.example.deliveryherotest.repository.api.IDeliveryHeroAPI
import com.example.deliveryherotest.repository.api.helper.ITransformer
import com.example.deliveryherotest.repository.api.helper.NetworkBoundResource
import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.api.model.RestaurantsResponse
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.Flowable
import retrofit2.Response
import javax.inject.Inject

class DataRepository
@Inject constructor(val deliveryAPI: IDeliveryHeroAPI,
                    val appDataBase: InAppDataBase,
                    val schedulersProvider: ISchedulers,
                    val sharedPreferences: SharedPreferences,
                    var restaurantToEntityTransformer: ITransformer<Restaurant, RestaurantEntity>,
                    var entityToRestaurantTransformerTransformer: ITransformer<RestaurantEntity, Restaurant>)
    : IDataRepository {

    override fun fetchRestaurants(isOnline: Boolean): Flowable<Resource<RestaurantsResponse>> {
        return object : NetworkBoundResource<RestaurantsResponse, RestaurantsResponse>(schedulersProvider) {
            override fun saveCallResult(request: RestaurantsResponse) {
                appDataBase.restaurantDAO().insertAll(request.items.map {
                    it?.let {
                        restaurantToEntityTransformer.transform(it)
                    }
                })
            }

            override fun loadFromDb(): Flowable<RestaurantsResponse> {
                var list = appDataBase.restaurantDAO().getAll().map {
                    entityToRestaurantTransformerTransformer.transform(it)
                }
                return Flowable.just(RestaurantsResponse(null, list))
            }

            override fun shouldFetch(): Boolean {
                return isOnline
            }

            override fun createCall(): Flowable<Response<RestaurantsResponse>> = deliveryAPI.getRestaurants()
        }.asFlowable()
    }

    override fun getRestaurantDetails(id: Int) {
        TODO("Not yet implemented")
    }

    override fun checkAndFetchConfiguration() {
        TODO("Not yet implemented")
    }
}