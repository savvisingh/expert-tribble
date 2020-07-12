package com.example.deliveryherotest.utils

import com.example.deliveryherotest.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class DeliveryHeroApplication: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<DeliveryHeroApplication> {
        return DaggerApplicationComponent.factory().create(this)
    }
}