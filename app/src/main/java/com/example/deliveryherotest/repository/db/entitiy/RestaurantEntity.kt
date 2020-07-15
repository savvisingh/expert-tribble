package com.example.deliveryherotest.repository.db.entitiy

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RestaurantEntity(@PrimaryKey val id : Int,
                            val name : String,
                            val image : String,
                            @ColumnInfo(name = "average_rating") val averageRating : Double,
                            @ColumnInfo(name ="total_reviews") val totalReviews : Int,
                            @ColumnInfo(name ="top_cuisines") val topCuisinesString : String,
                            @ColumnInfo(name ="distance_in_meters") val distanceInMeters : Int,
                            @ColumnInfo(name ="price_tier") val priceTier : Int,
                            @ColumnInfo(name ="popularity_score") val popularityScore : Int,
                            var menuString : String?,
                            var isFavourite: Boolean = false)