package com.android.careaxiomtest

import android.app.Application
import com.android.careaxiomtest.di.component.AppComponent
import com.android.careaxiomtest.di.component.DaggerAppComponent
import com.android.careaxiomtest.di.module.NetworkModule


class MainApp : Application() {

    companion object {
        lateinit var appCompnent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appCompnent = DaggerAppComponent.builder().buildNetworkModule(NetworkModule(applicationContext)).build()
    }

}