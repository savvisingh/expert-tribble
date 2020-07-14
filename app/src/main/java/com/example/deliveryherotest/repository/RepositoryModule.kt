package com.example.deliveryherotest.repository

import com.example.deliveryherotest.repository.db.InAppDataBaseModule
import com.example.deliveryherotest.repository.api.APIModule
import com.example.deliveryherotest.repository.api.transformer.TransformerModule
import com.example.deliveryherotest.repository.config.ConfigManager
import com.example.deliveryherotest.repository.config.IConfigManager
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
    fun getConfigManager(configManager: ConfigManager): IConfigManager{
        return configManager
    }
}