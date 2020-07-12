package com.example.deliveryherotest.repository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Filters(
    @SerialName("Nearby") val nearby: Nearby,
    @SerialName("Popular") val popular: Popular,
    @SerialName("Top Reviews") val topReviews: TopReviews
)