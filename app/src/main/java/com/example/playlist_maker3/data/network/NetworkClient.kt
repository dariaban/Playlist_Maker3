package com.example.playlist_maker3.data.network

import com.example.playlist_maker3.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}