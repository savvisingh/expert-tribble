package com.example.deliveryherotest.repository

import com.example.deliveryherotest.repository.api.helper.Resource
import com.example.deliveryherotest.repository.api.model.*
import io.reactivex.Completable
import io.reactivex.Flowable

interface IDataRepository {

    fun getRestaurantDetails(id: Int): Flowable<Resource<Restaurant>>

    fun checkAndFetchConfiguration(): Flowable<Resource<Boolean>>

    fun fetchRestaurants(): Flowable<Resource<Filters>>

    fun filterRestaurants(filter: FilterData): Flowable<Resource<List<Restaurant?>>>

    fun markFavourite(isFavourite: Boolean, restaurantId: Int): Completable
}