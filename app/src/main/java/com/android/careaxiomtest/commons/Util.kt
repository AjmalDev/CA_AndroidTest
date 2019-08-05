package com.android.careaxiomtest.commons

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo


class Util
{
    companion object
    {
        fun hasNetwork(context: Context): Boolean? {
            val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }
    }

}