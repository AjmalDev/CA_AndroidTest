package com.android.careaxiomtest.network

import com.android.careaxiomtest.commons.API_GET_PHOTOS
import com.android.careaxiomtest.data.Photo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(API_GET_PHOTOS)
    fun getPhotos(@Query("albumId") id: Long): Single<List<Photo>>

}
