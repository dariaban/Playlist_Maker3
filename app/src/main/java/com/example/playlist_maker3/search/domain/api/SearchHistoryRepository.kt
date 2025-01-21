package com.example.playlist_maker3.search.domain.api

import com.example.playlist_maker3.search.domain.model.Track

interface SearchHistoryRepository {
    fun saveHistory(history: MutableList<Track>)
    fun getTrackHistory(): ArrayList<Track>
    fun clearHistory()
    fun addTrack(track: Track)
}