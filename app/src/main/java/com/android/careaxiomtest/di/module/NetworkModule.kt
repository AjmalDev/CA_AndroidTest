package com.android.careaxiomtest.di.module

import android.content.Context
import com.android.careaxiomtest.commons.BASE_URL
import com.android.careaxiomtest.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule constructor(val context: Context) {

    @Provides
    @Singleton
    fun provideRetrofitInterface(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    
    @Provides
    @Reusable
    fun providesNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

}