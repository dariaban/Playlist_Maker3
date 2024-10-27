package com.example.playlist_maker3.domain.api

import com.example.playlist_maker3.domain.models.Track

interface SearchHistoryRepository{
     fun saveHistory(history: MutableList<Track>)
     fun addTrack(track: Track)
     fun getTrackHistory(): ArrayList<Track>
     fun clearHistory()
}