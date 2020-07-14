package com.example.deliveryherotest.repository.api.transformer

import com.example.deliveryherotest.repository.api.helper.ITransformer
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.stringify
import javax.inject.Inject

class RestaurantToEntityTransformer @Inject constructor():
    ITransformer<Restaurant, RestaurantEntity> {
    @ImplicitReflectionSerializer
    override fun transform(t: Restaurant): RestaurantEntity {
        val json = Json(JsonConfiguration.Stable)
        return RestaurantEntity(t.id, t.name, t.image, t.averageRating, t.totalReviews,
            json.stringify(t.topCuisines), t.distanceInMeters, t.priceTier, t.popularityScore, json.stringify(t.menu), t.isFavourite)
    }
}