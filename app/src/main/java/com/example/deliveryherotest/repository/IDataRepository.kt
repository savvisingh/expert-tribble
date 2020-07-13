package com.example.deliveryherotest.repository

import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.RestaurantsResponse
import io.reactivex.Flowable

interface IDataRepository {

    fun getRestaurantDetails(id: Int)

    fun checkAndFetchConfiguration()

    fun fetchRestaurants(isOnline: Boolean): Flowable<Resource<RestaurantsResponse>>
}