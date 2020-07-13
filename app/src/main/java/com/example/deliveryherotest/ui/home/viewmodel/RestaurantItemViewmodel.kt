package com.example.deliveryherotest.ui.home.viewmodel

import androidx.databinding.ObservableBoolean
import com.example.deliveryherotest.R
import com.example.deliveryherotest.base.BaseViewModel
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.utils.roundTo

class RestaurantItemViewModel constructor(val restaurant: Restaurant,
                                          val favouriteClickAction: (Boolean, Int) -> Unit,
                                          val itemClickAction: (Int) -> Unit): BaseViewModel(R.layout.item_restaurant_layout) {

    val imageUrl = restaurant.image
    val restaurantName = restaurant.name
    val averageRating = restaurant.averageRating
    val noOfReviews = "${restaurant.totalReviews} reviews"
    var distance: String = "${(restaurant.distanceInMeters/1000.0).roundTo(1)} km"
    var cuisinesText = ""
    var favourite = ObservableBoolean(restaurant.isFavourite)

    init {
        restaurant.topCuisines.forEachIndexed { index, s ->
            cuisinesText += s
            if(index != restaurant.topCuisines.size-1){
                cuisinesText += " | "
            }
        }

    }

    var favouriteClick = {
        favourite.set(!favourite.get())
        favouriteClickAction.invoke(favourite.get(), restaurant.id)
    }

    var itemClick = {
        itemClickAction.invoke(restaurant.id)
    }

}