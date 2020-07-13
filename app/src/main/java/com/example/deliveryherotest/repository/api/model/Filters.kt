package com.example.deliveryherotest.repository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Filters(
    @SerialName("Nearby") val nearby: FilterData?,
    @SerialName("Popular") val popular: FilterData?,
    @SerialName("Top Reviews") val topReviews: FilterData?
)