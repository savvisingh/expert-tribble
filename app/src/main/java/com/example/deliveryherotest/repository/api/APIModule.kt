package com.example.deliveryherotest.repository.api

import com.example.deliveryherotest.utils.scheduler.ISchedulers
import com.example.deliveryherotest.utils.scheduler.Schedulers
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
object APIModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideRetrofit(): Retrofit {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://demo-api.deliveryherotech.com/")
            .addConverterFactory(
                Json.nonstrict
                .asConverterFactory(MediaType.get("application/json")))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit
    }

    @Provides
    @JvmStatic
    @Singleton
    fun provideAPI(retrofit: Retrofit): IDeliveryHeroAPI{
        return retrofit.create(IDeliveryHeroAPI::class.java)
    }


    @Provides
    @JvmStatic
    @Singleton
    fun provideSchedulers(schedulers: Schedulers) : ISchedulers {
        return schedulers
    }

}