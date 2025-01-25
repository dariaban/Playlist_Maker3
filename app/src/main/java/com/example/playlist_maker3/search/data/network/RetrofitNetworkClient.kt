package com.example.playlist_maker3.search.data.network

import com.example.playlist_maker3.search.data.dto.Response
import com.example.playlist_maker3.search.data.dto.TrackSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitNetworkClient : NetworkClient {
    private val itunesApiBaseUrl = "https://itunes.apple.com/"
    private val retrofit = Retrofit.Builder().baseUrl(itunesApiBaseUrl)
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val itunesApi = retrofit.create(ItunesApiService::class.java)

    override fun doRequest(dto: Any): Response {
        return try {
            if (dto is TrackSearchRequest) {
                val resp = itunesApi.search(dto.expression).execute()
                val body = resp.body() ?: Response()
                return body.apply { resultCode = resp.code() }
            } else {
                return Response().apply {
                    resultCode = 400
                }
            }
        } catch (e: IOException) {
            Response().apply {
                resultCode = 500
            }
        }
    }

}