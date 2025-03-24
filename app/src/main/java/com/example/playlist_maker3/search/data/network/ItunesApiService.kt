package com.example.playlist_maker3.search.data.network

import com.example.playlist_maker3.search.data.dto.TrackSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {
    @GET("search?entity=song")
   suspend fun search(
        @Query("term") text: String,
        @Query("country") country: String = "RU",
        @Query("media") media: String = "music",
    ): TrackSearchResponse

}