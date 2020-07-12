package com.example.deliveryherotest.repository.api.model

import androidx.room.Entity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Restaurant (val id : Int,
                       val name : String,
                       val image : String,
                       @SerialName("average_rating")
                       val averageRating : Double,
                       @SerialName("total_reviews")
                       val totalReviews : Int,
                       @SerialName("top_cuisines")
                       val topCuisines : List<String>,
                       @SerialName("distance_in_meters")
                       val distanceInMeters : Int,
                       @SerialName("price_tier")
                       val priceTier : Int,
                       @SerialName("popularity_score")
                       val popularityScore : Int,
                       val menu : List<MenuCategory> = listOf())