package com.example.playlist_maker3.search.data.network

import com.example.playlist_maker3.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}