package com.example.playlist_maker3.search.domain.api

import com.example.playlist_maker3.search.domain.model.Track

interface SearchHistoryRepository {
    fun saveHistory(history: List<Track>)
    fun getTrackHistory(): List<Track>
    fun clearHistory()
    fun addTrack(track: Track)
}