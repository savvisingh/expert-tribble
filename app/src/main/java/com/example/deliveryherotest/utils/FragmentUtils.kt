package com.example.deliveryherotest.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class FragmentUtils {

    companion object {

        fun startFragment(containerId: Int, activity: FragmentActivity, initializer: () -> Fragment, TAG: String) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            var fragment = activity.supportFragmentManager.findFragmentByTag(TAG)
            if (fragment == null) {
                fragment = initializer.invoke()
                transaction.add(containerId, fragment, TAG)
                transaction.addToBackStack(TAG)
            } else {
                activity.supportFragmentManager.popBackStack(TAG, 0)
            }
            transaction.commit()
        }
    }
}