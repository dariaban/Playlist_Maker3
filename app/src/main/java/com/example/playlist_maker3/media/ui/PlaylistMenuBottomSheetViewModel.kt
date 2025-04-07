package com.example.playlist_maker3.media.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.media.domain.db.PlaylistsInteractor
import com.example.playlist_maker3.media.domain.model.Playlist
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlaylistMenuBottomSheetViewModel (
    private val playListsInteractor: PlaylistsInteractor
): ViewModel() {

    var isClickable = true

    fun clickDebounce(): Boolean {
        val current = isClickable
        if (isClickable) {
            isClickable = false
            viewModelScope.launch {
                delay(Constants.CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickable = true
            }
        }
        return current
    }

    fun deletePlaylist(playlist: Playlist, onResultListener: () -> Unit) {
        viewModelScope.launch {
            playListsInteractor.deletePlaylist(playlist)
            onResultListener()
        }
    }
}