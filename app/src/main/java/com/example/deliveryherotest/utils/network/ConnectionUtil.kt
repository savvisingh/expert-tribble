package com.example.deliveryherotest.utils.network

import android.content.Context
import android.net.ConnectivityManager

class ConnectionUtil(var context: Context): IConnectionUtil {

    override fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}