package com.example.playlist_maker3.search.domain.api

import com.example.playlist_maker3.search.domain.model.Track

interface TrackRepository {
    fun searchTracks(expression: String): List<Track>

}