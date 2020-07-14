package com.example.deliveryherotest.repository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Configurations(
    @SerialName("add_to_favorites_enabled") val addToFavoritesEnabled: Boolean = false,
    @SerialName("currency_symbol") val currencySymbol: String = "",
    @SerialName("max_price_tiers") val maxPriceTiers: Int = 0,
    @SerialName("max_results_cache_time_seconds") val maxResultsCacheTimeSeconds: String = ""
)