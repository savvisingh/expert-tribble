package com.example.deliveryherotest.repository

import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.FilterData
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.api.model.RestaurantsResponse
import io.reactivex.Flowable

interface IDataRepository {

    fun getRestaurantDetails(id: Int): Flowable<Resource<Restaurant>>

    fun checkAndFetchConfiguration()

    fun fetchRestaurants(): Flowable<Resource<RestaurantsResponse>>

    fun filterRestaurants(filter: FilterData): Flowable<Resource<List<Restaurant?>>>

    fun markFavourite(isFavourite: Boolean, restaurantId: Int)
}