package com.example.playlist_maker3.media.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker3.media.domain.PlaylistsScreenState
import com.example.playlist_maker3.media.domain.db.PlaylistsInteractor
import com.example.playlist_maker3.media.domain.model.Playlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistsViewModel(private val interactor: PlaylistsInteractor) :
    ViewModel() {
    private val _contentFlow: MutableStateFlow<PlaylistsScreenState> =
        MutableStateFlow(PlaylistsScreenState.Empty)

    val contentFlow: StateFlow<PlaylistsScreenState> = _contentFlow
    var isClickable = true

    init {
        fillData()
    }

    private fun fillData() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor
                .getPlaylists()
                .collect { playlists ->
                    processResult(playlists)
                }
        }
    }

    private fun processResult(playlists: List<Playlist>) {
        if (playlists.isEmpty()) {
            _contentFlow.value = (PlaylistsScreenState.Empty)
        } else {
            _contentFlow.value = (PlaylistsScreenState.Content(playlists))
        }
    }

    fun onPlaylistClick() {
        isClickable = false
    }
}