package com.example.playlist_maker3.search.data

import com.example.playlist_maker3.search.dto.TrackSearchRequest
import com.example.playlist_maker3.search.dto.TrackSearchResponse
import com.example.playlist_maker3.search.network.NetworkClient
import com.example.playlist_maker3.search.domain.api.TrackRepository
import com.example.playlist_maker3.search.domain.model.Track
import java.io.IOException

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {

    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        return if (response.resultCode == 200) {
            (response as TrackSearchResponse).results.map {
                Track(
                    it.trackId,
                    it.artistName,
                    it.collectionName,
                    it.previewUrl,
                    it.artworkUrl100,
                    it.trackName,
                    it.releaseDate,
                    it.trackTimeMillis,
                    it.country,
                    it.primaryGenreName
                )
            }
        } else {
            throw IOException("Network error with code: ${response.resultCode}")
        }
    }
}