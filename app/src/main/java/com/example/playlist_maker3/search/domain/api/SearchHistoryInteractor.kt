package com.example.playlist_maker3.search.domain.api

import com.example.playlist_maker3.search.domain.model.Track

interface SearchHistoryInteractor {
    fun getHistory(): ArrayList<Track>
    fun addTrack(track: Track)
    fun saveHistory()
    fun clearHistory()
}