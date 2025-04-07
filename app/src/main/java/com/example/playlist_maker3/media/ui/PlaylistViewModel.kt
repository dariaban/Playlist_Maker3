package com.example.playlist_maker3.media.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.media.domain.PlaylistState
import com.example.playlist_maker3.media.domain.db.PlaylistsInteractor
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class PlaylistViewModel(
    private val playlistsInteractor: PlaylistsInteractor,
) : ViewModel() {

    private val stateLiveData = MutableLiveData<PlaylistState>()

    var isClickable = true

    fun observeState(): LiveData<PlaylistState> = stateLiveData

    private fun renderState(state: PlaylistState) {
        stateLiveData.postValue(state)
    }

    fun requestPlayListInfo(playlistId: Int) {
        viewModelScope.launch {
            renderState(
                PlaylistState.PlaylistInfo(
                    playlistsInteractor.getPlaylist(playlistId)
                )
            )
            renderState(
                PlaylistState.PlaylistTracks(
                    playlistsInteractor.getPlaylistTracks(playlistId)
                )
            )
        }
    }

    fun deleteTrack(trackId: Int, playlistId: Int) {
        viewModelScope.launch {
            playlistsInteractor.deleteTrack(trackId, playlistId)
            renderState(
                PlaylistState.PlaylistTracks(
                    playlistsInteractor.getPlaylistTracks(playlistId)
                )
            )
            renderState(
                PlaylistState.PlaylistInfo(
                    playlistsInteractor.getPlaylist(playlistId)
                )
            )
        }
    }

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

}