package com.android.careaxiomtest.commons

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class Util
{
    companion object
    {
        fun hasNetwork(context: Context): Boolean? {
            var isConnected: Boolean? = false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetwork != null && activeNetwork.isConnected)
                isConnected = true
            return isConnected
        }
    }

}