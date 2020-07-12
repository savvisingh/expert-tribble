package com.example.deliveryherotest.repository

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.deliveryherotest.repository.db.InAppDataBaseModule
import com.example.deliveryherotest.repository.api.APIModule
import com.example.deliveryherotest.repository.api.transformer.TransformerModule
import com.example.deliveryherotest.utils.DeliveryHeroApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [APIModule::class, InAppDataBaseModule::class, TransformerModule::class])
object RepositoryModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDataRepo(repo: DataRepository): IDataRepository{
        return repo
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideSharedPref(application: DeliveryHeroApplication): SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(application)
    }
}