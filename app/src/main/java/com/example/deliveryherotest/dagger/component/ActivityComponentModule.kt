package com.example.deliveryherotest.dagger.component

import com.example.deliveryherotest.ui.HomeActivity
import com.example.deliveryherotest.dagger.scope.ActivityScope
import com.example.deliveryherotest.ui.HomeActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityComponentModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun bindHomeActivity(): HomeActivity
}