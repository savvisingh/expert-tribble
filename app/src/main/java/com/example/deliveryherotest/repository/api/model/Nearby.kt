package com.example.deliveryherotest.repository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Nearby(
    @SerialName("filter_by") val filterBy: String,
    @SerialName("sort_dir") val sortDir: String
)