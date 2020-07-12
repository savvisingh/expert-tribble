package com.example.deliveryherotest.repository.db

import com.example.deliveryherotest.repository.db.dao.RestaurantDAO
import com.example.deliveryherotest.utils.DeliveryHeroApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object InAppDataBaseModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideDataBase(application: DeliveryHeroApplication): InAppDataBase {
        return InAppDataBase.getDatabase(application.applicationContext)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRestaurantDao(application: InAppDataBase): RestaurantDAO {
        return application.restaurantDAO()
    }
}