package com.example.playlist_maker3.domain.impl

import com.example.playlist_maker3.data.repository.SearchHistoryRepository
import com.example.playlist_maker3.domain.api.SearchHistoryInteractor
import com.example.playlist_maker3.domain.models.Track


class SearchHistoryInteractorImpl(private val searchHistoryRepository: SearchHistoryRepository):
    SearchHistoryInteractor{
    override fun getHistory(): ArrayList<Track>{
        return searchHistoryRepository.getTrackHistory()
    }
    override fun saveHistory() {
        searchHistoryRepository.saveHistory(getHistory())
    }

    override fun clearHistory() {
        searchHistoryRepository.clearHistory()
    }

    override fun addTrack(track: Track) {
    searchHistoryRepository.addTrack(track)
    }

}
