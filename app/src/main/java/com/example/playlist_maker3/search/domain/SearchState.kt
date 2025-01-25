package com.example.playlist_maker3.search.domain

import com.example.playlist_maker3.search.domain.model.Track

sealed class SearchState {
    data object Loading : SearchState()
    data class SearchList(val tracks: List<Track>) : SearchState()
    data class HistoryList(val historyTracks: List<Track>): SearchState()
    data object NetworkError : SearchState()
    data object NothingFound : SearchState()
    data object Empty : SearchState()
}