package com.example.deliveryherotest.ui.home.viewmodel

import com.example.deliveryherotest.R
import com.example.deliveryherotest.base.BaseViewModel
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.utils.roundTo

class RestaurantItemViewModel constructor(val restaurant: Restaurant): BaseViewModel(R.layout.item_restaurant_layout) {

    val imageUrl = restaurant.image
    val restaurantName = restaurant.name
    val averageRating = restaurant.averageRating
    val noOfReviews = "${restaurant.totalReviews} reviews"
    var distance: String = "${(restaurant.distanceInMeters/1000.0).roundTo(1)} km"
    var cuisinesText = ""

    init {
        restaurant.topCuisines.forEachIndexed { index, s ->
            cuisinesText += s
            if(index != restaurant.topCuisines.size-1){
                cuisinesText += " | "
            }
        }
    }
}