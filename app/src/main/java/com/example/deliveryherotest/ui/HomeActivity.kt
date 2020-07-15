package com.example.deliveryherotest.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.text.isDigitsOnly
import com.example.deliveryherotest.R
import com.example.deliveryherotest.ui.deeplink.DailyDeeplinkHandler
import com.example.deliveryherotest.ui.deeplink.DailyDeeplinkHandler.DEEPLINK_BUNDLE_EXTRA_LANDING_FRAGMENT
import com.example.deliveryherotest.ui.deeplink.DailyDeeplinkHandler.DEEPLINK_EXTRA_ID
import com.example.deliveryherotest.ui.deeplink.DailyDeeplinkHandler.SCREEN_NAME_DETAILS
import com.example.deliveryherotest.ui.details.DetailsFragment
import com.example.deliveryherotest.ui.home.HomeFragment
import com.example.deliveryherotest.utils.FragmentUtils
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
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        var newIntent: Intent? = null

        if (Intent.ACTION_VIEW == intent?.action && intent.dataString != null) {
            newIntent = DailyDeeplinkHandler.convertUrlToParams(intent)
        }

        setIntent(newIntent)

        handleExtra(newIntent?.extras)
    }

    private fun handleExtra(extras: Bundle?) {

        if (extras == null) {
            return
        }

        if (extras.containsKey(DEEPLINK_BUNDLE_EXTRA_LANDING_FRAGMENT)){
            val launchFragment = extras.getString(DEEPLINK_BUNDLE_EXTRA_LANDING_FRAGMENT, "")
            when(launchFragment){
                SCREEN_NAME_DETAILS -> {
                    val id = extras.getString(DEEPLINK_EXTRA_ID)
                    if(!id.isNullOrEmpty() &&  id.isDigitsOnly()){
                        FragmentUtils.startFragment(R.id.container, this,
                            { DetailsFragment.newInstance(id.toInt()) }, DetailsFragment.TAG)
                    }

                }
            }
        }
    }
}