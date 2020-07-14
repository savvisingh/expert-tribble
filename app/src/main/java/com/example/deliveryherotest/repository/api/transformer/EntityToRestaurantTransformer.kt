package com.example.deliveryherotest.repository.api.transformer

import com.example.deliveryherotest.repository.api.helper.ITransformer
import com.example.deliveryherotest.repository.api.model.MenuCategory
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parseList
import javax.inject.Inject

class EntityToRestaurantTransformer @Inject constructor():
    ITransformer<RestaurantEntity, Restaurant> {
    @ImplicitReflectionSerializer
    override fun transform(t: RestaurantEntity): Restaurant {

        val json = Json(JsonConfiguration.Stable)
        val listOfCuisines = json.parseList<String>(t.topCuisinesString)
        var menuList = listOf<MenuCategory>()
        t.menuString?.let {
            menuList = json.parseList(t.menuString)
        }


        return Restaurant(t.id, t.name, t.image, t.averageRating, t.totalReviews,
            listOfCuisines, t.distanceInMeters, t.priceTier, t.popularityScore, menuList, t.isFavourite)
    }
}