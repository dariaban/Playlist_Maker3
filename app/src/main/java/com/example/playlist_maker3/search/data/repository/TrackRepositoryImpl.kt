package com.example.playlist_maker3.search.data.repository

import com.example.playlist_maker3.search.data.dto.TrackSearchRequest
import com.example.playlist_maker3.search.data.dto.TrackSearchResponse
import com.example.playlist_maker3.search.data.mapper.TrackMapper
import com.example.playlist_maker3.search.data.network.NetworkClient
import com.example.playlist_maker3.search.domain.api.TrackRepository
import com.example.playlist_maker3.search.domain.model.Track
import java.io.IOException

class TrackRepositoryImpl(private val networkClient: NetworkClient, private val trackMapper: TrackMapper) : TrackRepository {

    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        return if (response.resultCode == 200) {
            (response as TrackSearchResponse).results.map {
               trackMapper.trackMap(it)
            }
        } else {
            throw IOException("Network error with code: ${response.resultCode}")
        }
    }
}