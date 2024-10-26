package com.example.playlist_maker3.domain.api

import com.example.playlist_maker3.domain.models.Track

interface TracksInteractor {
    fun searchTracks (expression: String, consumer: TracksConsumer)

    interface TracksConsumer {
        fun consume (foundTracks: List<Track>)
        fun onFailure(t:Throwable)
    }
}