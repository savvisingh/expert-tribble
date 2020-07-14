package com.example.deliveryherotest.repository.api

import com.example.deliveryherotest.repository.api.model.Configurations
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.api.model.RestaurantsResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IDeliveryHeroAPI {

    @GET("v1/config.json")
    fun getConfigurations(): Flowable<Response<Configurations>>

    @GET("v1/restaurants.json")
    fun getRestaurants(): Flowable<Response<RestaurantsResponse>>

    @GET("v1/restaurants/{restaurantId}.json")
    fun getRestaurantDetails(@Path("restaurantId") restaurantId: String) : Flowable<Response<Restaurant>>

}