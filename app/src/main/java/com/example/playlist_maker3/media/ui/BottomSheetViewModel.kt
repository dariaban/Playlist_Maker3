package com.example.playlist_maker3.media.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.media.domain.PlaylistsState
import com.example.playlist_maker3.media.domain.db.PlaylistsInteractor
import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.search.domain.model.Track
import com.example.playlist_maker3.search.utils.debounce
import kotlinx.coroutines.launch

class PlaylistsBottomSheetViewModel(
    private val interactor: PlaylistsInteractor
) : ViewModel() {
    private val playlistsStateLiveData = MutableLiveData<PlaylistsState>()

    fun observePlaylistsState(): LiveData<PlaylistsState> = playlistsStateLiveData

    var isClickable = true

    private val playlistClickDebounce =
        debounce<Boolean>(Constants.CLICK_DEBOUNCE_DELAY_MILLIS, viewModelScope, false) {
            isClickable = it
        }

    fun requestPlaylists() {
        viewModelScope.launch {
            val playlists = interactor.getPlaylists()
            if (playlists.isEmpty()) {
                playlistsStateLiveData.postValue(PlaylistsState.Empty)
            } else {
                playlistsStateLiveData.postValue(PlaylistsState.Playlists(playlists))
            }
        }
    }

    fun addTrackToPlaylist(track: Track, playlist: Playlist) {
        viewModelScope.launch {
            if (interactor.isTrackAlreadyExists(track.trackId, playlist.playlistId)) {
                playlistsStateLiveData.postValue(
                    PlaylistsState.AddTrackResult(false, playlistName = playlist.name)
                )
            } else {
                interactor.addTrack(track, playlist.playlistId)
                playlistsStateLiveData.postValue(
                    PlaylistsState.AddTrackResult(true, playlistName = playlist.name)
                )
            }
        }
    }

    fun onPlaylistClicked() {
        isClickable = false
        playlistClickDebounce(true)
    }

}