package com.android.careaxiomtest.di.component

import com.android.careaxiomtest.ui.MainActivity
import com.android.careaxiomtest.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [(NetworkModule::class)])
interface AppComponent {

    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun buildNetworkModule(module: NetworkModule): Builder
    }

}