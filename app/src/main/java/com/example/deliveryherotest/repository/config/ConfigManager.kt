package com.example.deliveryherotest.repository.config

import android.content.SharedPreferences
import com.example.deliveryherotest.repository.api.model.Configurations
import javax.inject.Inject

class ConfigManager
@Inject constructor(val sharedPreferences: SharedPreferences): IConfigManager {

    companion object{
        const val PARAM_CACHE_TIME = "max_results_cache_time_seconds"
        const val PARAM_MAX_TIER = "max_price_tiers"
        const val PARAM_CURRENCY_SYMBOL = "currency_symbol"
        const val PARAM_FAVOURITE_ENABLED = "add_to_favorites_enabled"
        const val PARAM_LAST_CONFIG_FETCH = "last_config_fetch_time"
    }

    override fun shouldFetch(): Boolean {
        return if(sharedPreferences.contains(PARAM_LAST_CONFIG_FETCH) && sharedPreferences.contains(PARAM_CACHE_TIME)){
            val lastFetchTime = sharedPreferences.getLong(PARAM_LAST_CONFIG_FETCH, 0)
            val expiryTime = sharedPreferences.getLong(PARAM_CACHE_TIME, 0)
            lastFetchTime + expiryTime <= System.currentTimeMillis()
        } else {
            true
        }
    }

    override fun saveConfigurations(config: Configurations) {
        sharedPreferences.edit().also {
            it.putInt(PARAM_MAX_TIER, config.maxPriceTiers)
            it.putString(PARAM_CURRENCY_SYMBOL, config.currencySymbol)
            it.putLong(PARAM_CACHE_TIME, config.maxResultsCacheTimeSeconds.toLong())
            it.putBoolean(PARAM_FAVOURITE_ENABLED, config.addToFavoritesEnabled)
            it.putLong(PARAM_LAST_CONFIG_FETCH, System.currentTimeMillis())
        }.apply()
    }

    override fun isAddToFavEnabled(): Boolean {
        return sharedPreferences.getBoolean(PARAM_FAVOURITE_ENABLED, false)
    }

    override fun getCurrencySymbol(): String? {
        return sharedPreferences.getString(PARAM_CURRENCY_SYMBOL, "")
    }

    override fun getMaxPriceTier(): Int {
        return sharedPreferences.getInt(PARAM_MAX_TIER, 0)
    }
}