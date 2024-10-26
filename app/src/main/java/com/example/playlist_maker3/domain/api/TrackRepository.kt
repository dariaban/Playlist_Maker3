package com.example.playlist_maker3.domain.api

import com.example.playlist_maker3.domain.models.Track

interface TrackRepository {
    fun searchTracks (expression: String): List<Track>

}