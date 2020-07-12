package com.example.deliveryherotest.repo.network

import retrofit2.http.GET
import retrofit2.http.Path

interface IDeliveryHeroAPI {

    @GET("v1/config.json")
    fun getConfigurations()

    @GET("v1/restaurants.json")
    fun getRestaurants()

    @GET("v1/restaurants/{restaurantId}.json")
    fun getRestaurantDetails(@Path("restaurantId") restaurantId: String)
}