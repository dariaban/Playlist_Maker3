package com.example.playlist_maker3.search.domain.impl

import com.example.playlist_maker3.search.domain.api.SearchHistoryInteractor
import com.example.playlist_maker3.search.domain.api.SearchHistoryRepository
import com.example.playlist_maker3.search.domain.model.Track


class SearchHistoryInteractorImpl(private val searchHistoryRepository: SearchHistoryRepository) :
    SearchHistoryInteractor {
    override fun getHistory(): List<Track> {
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
