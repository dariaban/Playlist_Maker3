package com.example.playlist_maker3.domain.api

import com.example.playlist_maker3.domain.models.Track

interface SearchHistoryInteractor{
     fun getHistory(): ArrayList<Track>
     fun addTrack(track: Track)
     fun saveHistory()
     fun clearHistory()
}