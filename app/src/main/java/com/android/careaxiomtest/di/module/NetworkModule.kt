package com.android.careaxiomtest.di.module

import android.content.Context
import com.android.careaxiomtest.commons.BASE_URL
import com.android.careaxiomtest.commons.Util.Companion.hasNetwork
import com.android.careaxiomtest.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton




@Module
class NetworkModule constructor(val context: Context) {

    @Provides
    @Singleton
    fun provideRetrofitInterface(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)

            .build()
    }

    @Provides
    @Singleton
    fun provideRequestOkHttpClient(responseInterceptor: Interceptor): OkHttpClient {
        //Logging interceptor
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY;

        //Creating Cache sizes
        val cacheSize = (5 * 1024 * 1024).toLong()
        val myCache = Cache(context.cacheDir, cacheSize)

        //Ok Http Builder
        return OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(context)!!) {
                    request.newBuilder().header("pragma", "cache").header("Cache-Control", "public, max-age=" + 60)
                        .build()
                } else {
                    request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).removeHeader("pragma").header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 28
                    ).build()
                }
                chain.proceed(request)
            }
            .addNetworkInterceptor(responseInterceptor)
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideResponseInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            originalResponse.newBuilder()
                .removeHeader("pragma")
                .addHeader("pragma", "cache")
                .header(
                    "Cache-Control",
                    String.format("max-age=%d", 60)
                )
                .build()
        }
    }

    @Provides
    @Reusable
    fun providesNetworkService(retrofit: Retrofit): NetworkService {
        return retrofit.create(NetworkService::class.java)
    }

}