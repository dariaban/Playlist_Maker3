package com.example.playlist_maker3.media.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker3.media.domain.FavoriteState
import com.example.playlist_maker3.media.domain.db.FavoriteInteractor
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<FavoriteState>()

    fun observeState(): LiveData<FavoriteState> = stateLiveData

    private fun renderState(state: FavoriteState) {
        stateLiveData.postValue(state)
    }

    init{ fillData()}

    private fun processResult(tracks: List<Track>) {
        if (tracks.isEmpty()) {
            renderState(FavoriteState.Empty("Пока ничего не искали"))
        } else {
            renderState(FavoriteState.Content(tracks))
        }
    }

    fun fillData() {
        viewModelScope.launch {
            favoriteInteractor.historyTracks().collect { tracks -> processResult(tracks) }
        }
    }
}
