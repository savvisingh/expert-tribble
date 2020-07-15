package com.example.deliveryherotest.ui.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle

object DailyDeeplinkHandler {
    const val DEEPLINK_RESTAURANT_DETAILS = "restaurant"
    const val DEEPLINK_BUNDLE_EXTRA_LANDING_FRAGMENT = "landingActivity"
    const val DEEPLINK_EXTRA_ID = "restaurantId"

    const val SCREEN_NAME_DETAILS = "details"

    @JvmStatic
    fun convertUrlToParams(intent: Intent): Intent? {
        val action = intent.action
        val data = intent.dataString

        if (Intent.ACTION_VIEW == action && data != null){
            val uri = Uri.parse(data)
            var host = uri.host
            if (host == null) {
                host = ""
            }

            var args: Bundle? = null
            when (host){
                DEEPLINK_RESTAURANT_DETAILS -> {
                    args = Bundle()
                    args.putString(DEEPLINK_BUNDLE_EXTRA_LANDING_FRAGMENT, SCREEN_NAME_DETAILS)
                    args.putString(DEEPLINK_EXTRA_ID, uri.lastPathSegment)
                }
            }
            if (args != null) {
                intent.putExtras(args)
            }
        }

        return intent
    }


}