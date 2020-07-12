package com.example.deliveryherotest.repository.api.transformer

import com.example.deliveryherotest.repository.api.helper.ITransformer
import com.example.deliveryherotest.repository.api.model.Restaurant
import com.example.deliveryherotest.repository.db.entitiy.RestaurantEntity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TransformerModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideEntityToRestaurantTransformer(transformer: EntityToRestaurantTransformer):
            ITransformer<RestaurantEntity, Restaurant>{
        return transformer
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideRestaurantToEntityTransformer(transformer: RestaurantToEntityTransformer):
            ITransformer<Restaurant, RestaurantEntity> {
        return transformer
    }
}