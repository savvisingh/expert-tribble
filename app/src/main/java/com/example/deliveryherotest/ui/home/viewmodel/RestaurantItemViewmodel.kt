package com.example.deliveryherotest.ui.home.viewmodel

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.databinding.ObservableBoolean
import com.example.deliveryherotest.R
import com.example.deliveryherotest.base.BaseViewModel
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.config.IConfigManager
import com.example.deliveryherotest.utils.resource.IResourceService
import com.example.deliveryherotest.utils.roundTo

class RestaurantItemViewModel constructor(val restaurant: Restaurant,
                                          val favouriteClickAction: (Boolean, Int) -> Unit,
                                          val itemClickAction: (Int) -> Unit,
                                          var configManager: IConfigManager,
                                          var resourceService: IResourceService):
    BaseViewModel(R.layout.item_restaurant_layout) {

    val imageUrl = restaurant.image
    val restaurantName = restaurant.name
    val averageRating = restaurant.averageRating
    val noOfReviews = "${restaurant.totalReviews} reviews"
    var distance: String = "${(restaurant.distanceInMeters/1000.0).roundTo(1)} km"
    var cuisinesText = ""
    var isFavouriteEnabled = configManager.isAddToFavEnabled()
    var favourite = ObservableBoolean(restaurant.isFavourite)
    var spannable: SpannableString? = null

    init {
        restaurant.topCuisines.forEachIndexed { index, s ->
            cuisinesText += s
            if(index != restaurant.topCuisines.size-1){
                cuisinesText += " | "
            }
        }
        getTierPriceString()
    }

    var favouriteClick = {
        favourite.set(!favourite.get())
        favouriteClickAction.invoke(favourite.get(), restaurant.id)
    }

    var itemClick = {
        itemClickAction.invoke(restaurant.id)
    }

    private fun getTierPriceString(){
        val symbol = configManager.getCurrencySymbol()
        if(!symbol.isNullOrEmpty()){
            val baseString = symbol.repeat(configManager.getMaxPriceTier())
            spannable = SpannableString(baseString)
            spannable?.setSpan(ForegroundColorSpan(resourceService.getColor(R.color.charcoal_grey_light)), 0,
                baseString.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
            spannable?.setSpan(ForegroundColorSpan(resourceService.getColor(R.color.charcoal_grey)), 0,
                restaurant.priceTier, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
        }

    }
}