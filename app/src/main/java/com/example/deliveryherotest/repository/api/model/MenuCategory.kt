package com.example.deliveryherotest.repository.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuCategory(
    @SerialName("category_title") val categoryTitle: String,
    val products: List<Product>
)