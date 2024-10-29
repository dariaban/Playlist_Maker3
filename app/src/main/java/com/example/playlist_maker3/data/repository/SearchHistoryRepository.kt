package com.example.playlist_maker3.data.repository

import com.example.playlist_maker3.domain.models.Track

interface SearchHistoryRepository{
    fun saveHistory(history: MutableList<Track>)
    fun getTrackHistory(): ArrayList<Track>
    fun clearHistory()
}