package com.example.deliveryherotest.repository

import android.content.SharedPreferences
import com.example.deliveryherotest.repository.db.InAppDataBase
import com.example.deliveryherotest.repository.api.IDeliveryHeroAPI
import com.example.deliveryherotest.repository.api.helper.DbBoundResource
import com.example.deliveryherotest.repository.api.helper.ITransformer
import com.example.deliveryherotest.repository.api.helper.NetworkDbBoundResource
import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.FilterData
import com.example.deliveryherotest.repository.api.model.Filters
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.api.model.RestaurantsResponse
import com.example.deliveryherotest.repository.db.dao.QueryBuilder
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity
import com.example.deliveryherotest.utils.network.IConnectionUtil
import com.example.deliveryherotest.utils.scheduler.ISchedulers
import io.reactivex.Flowable
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parse
import kotlinx.serialization.stringify
import retrofit2.Response
import javax.inject.Inject

class DataRepository
@Inject constructor(val deliveryAPI: IDeliveryHeroAPI,
                    val appDataBase: InAppDataBase,
                    val schedulersProvider: ISchedulers,
                    val sharedPreferences: SharedPreferences,
                    var connectionUtil: IConnectionUtil,
                    var restaurantToEntityTransformer: ITransformer<Restaurant, RestaurantEntity>,
                    var entityToRestaurantTransformerTransformer: ITransformer<RestaurantEntity, Restaurant>)
    : IDataRepository {

    companion object{
        const val KEY_FILTERS = "filters"
    }

    @ImplicitReflectionSerializer
    override fun fetchRestaurants(): Flowable<Resource<RestaurantsResponse>> {
        return object : NetworkDbBoundResource<RestaurantsResponse, RestaurantsResponse>(schedulersProvider) {
            override fun saveCallResult(request: RestaurantsResponse) {
                appDataBase.restaurantDAO().insertAll(request.items.map {
                    it?.let {
                        restaurantToEntityTransformer.transform(it)
                    }
                })
                request.filters?.let {
                    val json = Json(JsonConfiguration.Stable)
                    sharedPreferences.edit().putString(KEY_FILTERS, json.stringify(request.filters)).apply()
                }

            }

            override fun loadFromDb(): Flowable<RestaurantsResponse> {
                val list = appDataBase.restaurantDAO().getAll().map {
                    entityToRestaurantTransformerTransformer.transform(it)
                }

                val filtersJson = sharedPreferences.getString(KEY_FILTERS, "")
                var filters: Filters? = null
                if(!filtersJson.isNullOrEmpty()){
                    val json = Json(JsonConfiguration.Stable)
                    filters = json.parse(filtersJson)
                }

                return Flowable.just(RestaurantsResponse(filters, list))
            }

            override fun shouldFetch(): Boolean {
                return connectionUtil.isConnected()
            }

            override fun createCall(): Flowable<Response<RestaurantsResponse>> = deliveryAPI.getRestaurants()
        }.asFlowable()
    }

    override fun filterRestaurants(filter: FilterData): Flowable<Resource<List<Restaurant?>>> {
        return object : DbBoundResource<List<Restaurant?>>(schedulersProvider){
            override fun loadFromDb(): Flowable<List<Restaurant?>> {
                return appDataBase.restaurantDAO()
                    .getFilteredList(QueryBuilder.getFilteredQuery(filter))
                    .map {
                        val newList = ArrayList<Restaurant>()
                        it.forEach {
                            entityToRestaurantTransformerTransformer.transform(it).let {
                                newList.add(it)
                            }
                        }
                        newList
                    }
            }

        }.asFlowable()
    }

    override fun markFavourite(isFavourite: Boolean, restaurantId: Int) {
        val restaurantEntity = appDataBase.restaurantDAO().getRestaurant(restaurantId)
        restaurantEntity?.let {
            it.isFavourite = isFavourite
            appDataBase.restaurantDAO().update(it)
        }
    }

    override fun getRestaurantDetails(id: Int): Flowable<Resource<Restaurant>> {
        return object: NetworkDbBoundResource<Restaurant, Restaurant>(schedulersProvider){
            override fun saveCallResult(request: Restaurant) {
                appDataBase.restaurantDAO().update(restaurantToEntityTransformer.transform(request))
            }

            override fun loadFromDb(): Flowable<Restaurant> {
                return appDataBase.restaurantDAO().getRestaurantFlow(id)
                    .map { entityToRestaurantTransformerTransformer.transform(it) }

            }

            override fun shouldFetch(): Boolean {
                return connectionUtil.isConnected()
            }

            override fun createCall(): Flowable<Response<Restaurant>> = deliveryAPI.getRestaurantDetails(id.toString())
        }
            .asFlowable()
    }

    override fun checkAndFetchConfiguration() {
        TODO("Not yet implemented")
    }
}