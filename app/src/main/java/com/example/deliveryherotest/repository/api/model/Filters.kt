package com.example.deliveryherotest.repository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Filters(
    @SerialName("Nearby") val nearby: FilterData? = null,
    @SerialName("Popular") val popular: FilterData? = null,
    @SerialName("Top Reviews") val topReviews: FilterData? = null
)