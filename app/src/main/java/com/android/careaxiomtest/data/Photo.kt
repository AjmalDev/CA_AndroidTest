package com.android.careaxiomtest.data

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("albumId") val albumdId: Int, @SerializedName("id") val id: Int, @SerializedName("title") val title: String,
    @SerializedName("url") val url: String, @SerializedName("thumbnailUrl") val thumbnails: String
)

