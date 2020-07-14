package com.example.deliveryherotest.repository.config

import com.example.deliveryherotest.repository.api.model.Configurations

interface IConfigManager {
    fun shouldFetch(): Boolean
    fun saveConfigurations(config: Configurations)
    fun isAddToFavEnabled(): Boolean
    fun getCurrencySymbol(): String?
    fun getMaxPriceTier(): Int
}