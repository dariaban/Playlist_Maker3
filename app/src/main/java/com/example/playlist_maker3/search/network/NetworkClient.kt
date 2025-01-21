package com.example.playlist_maker3.search.network

import com.example.playlist_maker3.search.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}