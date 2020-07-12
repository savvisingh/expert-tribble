package com.example.deliveryherotest.repository.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val image: String,
    val ingredients: String,
    val name: String,
    val price: Double
)