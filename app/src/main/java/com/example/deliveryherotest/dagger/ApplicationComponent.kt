package com.example.deliveryherotest.dagger

import com.example.deliveryherotest.dagger.component.ActivityComponentModule
import com.example.deliveryherotest.dagger.viewmodel.ViewModelModule
import com.example.deliveryherotest.repository.RepositoryModule
import com.example.deliveryherotest.utils.DeliveryHeroApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ViewModelModule::class,
    ActivityComponentModule::class, RepositoryModule::class, ApplicationModule::class])
interface ApplicationComponent : AndroidInjector<DeliveryHeroApplication> {
    @Component.Factory
    interface Factory: AndroidInjector.Factory<DeliveryHeroApplication>
}