package com.example.playlist_maker3.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker3.search.domain.SearchState
import com.example.playlist_maker3.search.domain.api.SearchHistoryInteractor
import com.example.playlist_maker3.search.domain.api.TracksInteractor
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchViewModel(
    private val searchHistoryInteractor: SearchHistoryInteractor,
    private val trackInteractor: TracksInteractor
) : ViewModel() {
    private var searchJob: Job? = null
    private val tracks = ArrayList<Track>()
    private val stateLiveData = MutableLiveData<SearchState>()
    private var lastSearch: String? = null
    fun observeState(): LiveData<SearchState> = stateLiveData
    fun searchDebounce(changedText: String) {
        lastSearch = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchTrack(changedText)
        }

    }

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    private suspend fun searchTrack(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(SearchState.Loading)
            viewModelScope.launch {
                trackInteractor.searchTracks(newSearchText).collect { (foundTracks, errorMessage) ->
                    tracks.clear()
                    if (!foundTracks.isNullOrEmpty()) {
                        tracks.addAll(foundTracks)
                        renderState(SearchState.SearchList(tracks))
                    } else if (errorMessage != null) {
                        renderState(SearchState.NetworkError)
                    } else renderState(SearchState.NothingFound)
                }
            }
        }
    }


    fun addTrack(track: Track) {
        loadSearchHistory()
        searchHistoryInteractor.addTrack(track)
        val newHistory = searchHistoryInteractor.getHistory()
        renderState(SearchState.HistoryList(newHistory))
    }


    fun clearHistory() {
        searchHistoryInteractor.clearHistory()
    }

    fun loadSearchHistory() {
        val historyTracks = searchHistoryInteractor.getHistory()
        renderState(SearchState.HistoryList(historyTracks))
    }


    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}







