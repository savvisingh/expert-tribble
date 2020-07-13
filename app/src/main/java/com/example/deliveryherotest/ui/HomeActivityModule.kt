package com.example.deliveryherotest.ui

import com.example.deliveryherotest.dagger.scope.FragmentScope
import com.example.deliveryherotest.ui.details.DetailsFragment
import com.example.deliveryherotest.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindRestaurantListingFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun bindDetailsFragment(): DetailsFragment
}