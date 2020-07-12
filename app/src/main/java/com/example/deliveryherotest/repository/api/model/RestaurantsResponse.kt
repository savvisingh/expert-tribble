package com.example.deliveryherotest.repository.api.model

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantsResponse(
    val filters: Filters?,
    val items: List<Restaurant?>
)