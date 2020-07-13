package com.example.deliveryherotest.ui

import android.os.Bundle
import com.example.deliveryherotest.R
import com.example.deliveryherotest.ui.details.DetailsFragment
import com.example.deliveryherotest.ui.home.HomeFragment
import dagger.android.support.DaggerAppCompatActivity

class HomeActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, HomeFragment.newInstance())
                .commitNow()
        }
    }
}