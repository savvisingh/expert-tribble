package com.example.deliveryherotest.dagger

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.deliveryherotest.utils.DeliveryHeroApplication
import com.example.deliveryherotest.utils.resource.IResourceService
import com.example.deliveryherotest.utils.resource.ResourceService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideSharedPref(application: DeliveryHeroApplication): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideResourceService(application: DeliveryHeroApplication): IResourceService{
        return ResourceService(application)
    }

}