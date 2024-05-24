package com.example.playlist_maker3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("search?entity=song")
    fun search(
        @Query("term") text: String,
        @Query("country") country: String ="RU",
        @Query("media") media: String = "music",
    ): Call<MyData>

}