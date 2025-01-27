package com.example.playlist_maker3.search.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlist_maker3.search.domain.SearchState
import com.example.playlist_maker3.search.domain.api.SearchHistoryInteractor
import com.example.playlist_maker3.search.domain.api.TracksInteractor
import com.example.playlist_maker3.search.domain.model.Track


class SearchViewModel(
    private val searchHistoryInteractor: SearchHistoryInteractor,
    private val trackInteractor: TracksInteractor
) : ViewModel() {
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { lastSearch?.let { searchTrack(it) } }
    private val tracks = ArrayList<Track>()
    private val stateLiveData = MutableLiveData<SearchState>()
    private var lastSearch: String? = null
    fun observeState(): LiveData<SearchState> = stateLiveData
    fun searchDebounce(changedText: String) {
        lastSearch = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun renderState(state: SearchState) {
        stateLiveData.postValue(state)
    }

    private fun searchTrack(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(SearchState.Loading)
            trackInteractor.searchTracks(newSearchText, object : TracksInteractor.TracksConsumer {
                override fun consume(foundTracks: List<Track>) {
                    tracks.clear()
                    if (!foundTracks.isNullOrEmpty()) {
                        tracks.addAll(foundTracks)
                        renderState(SearchState.SearchList(tracks))
                    } else {
                        renderState(SearchState.Empty)
                    }
                }

                override fun onFailure(t: Throwable) {
                    renderState(SearchState.NetworkError)
                }
            })
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

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(SEARCH_REQUEST_TOKEN)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private val SEARCH_REQUEST_TOKEN = Any()

    }
}







