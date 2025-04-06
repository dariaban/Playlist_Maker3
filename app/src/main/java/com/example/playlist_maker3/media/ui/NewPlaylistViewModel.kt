package com.example.playlist_maker3.media.ui

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker3.media.domain.ScreenState
import com.example.playlist_maker3.media.domain.db.NewPlaylistInteractor
import com.example.playlist_maker3.media.domain.model.PermissionsResultState
import com.example.playlist_maker3.media.domain.model.Playlist
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.net.URI


class NewPlaylistViewModel(
    private val interactor: NewPlaylistInteractor,
) : ViewModel() {

    private val _screenStateFlow: MutableSharedFlow<ScreenState> = MutableSharedFlow(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val screenStateFlow = _screenStateFlow.asSharedFlow()
    private val _permissionStateFlow = MutableSharedFlow<PermissionsResultState>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val permissionStateFlow = _permissionStateFlow.asSharedFlow()

    private var coverImageUrl = ""
    private var playlistName = ""
    private var playlistDescription = ""
    private var tracksCount = 0

    private val register = PermissionRequester.instance()

    fun onPlaylistCoverClicked() {
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= 33) {
                register.request(Manifest.permission.READ_MEDIA_IMAGES)
            } else {
                register.request(Manifest.permission.READ_EXTERNAL_STORAGE)
            }.collect { result ->
                when (result) {
                    is PermissionResult.Granted -> {
                        _permissionStateFlow.emit(PermissionsResultState.GRANTED)
                    }

                    is PermissionResult.Denied.NeedsRationale -> _permissionStateFlow.emit(
                        PermissionsResultState.NEEDS_RATIONALE
                    )

                    is PermissionResult.Denied.DeniedPermanently -> _permissionStateFlow.emit(
                        PermissionsResultState.DENIED_PERMANENTLY
                    )

                    PermissionResult.Cancelled -> return@collect
                }
            }
        }
    }


    fun onPlaylistNameChanged(playlistName: String?) {
        this.playlistName = playlistName!!

        if (!playlistName.isNullOrEmpty()) {
            _screenStateFlow.tryEmit(ScreenState.HasContent())

        } else _screenStateFlow.tryEmit(ScreenState.Empty())
    }

    fun onPlaylistDescriptionChanged(playlistDescription: String?) {

        if (playlistDescription != null) {
            this.playlistDescription = playlistDescription
        }
    }



    fun onCreateBtnClicked() {
        viewModelScope.launch {
            val newPlaylist = Playlist(
                id = 0,
                coverImageUrl = coverImageUrl,
                playlistName = playlistName,
                playlistDescription = playlistDescription,
                trackList = mutableListOf(),
                tracksCount = tracksCount
            )
            Log.d("Playlist_item", newPlaylist.toString())
            interactor.create(
                newPlaylist
            )
            _screenStateFlow.emit(ScreenState.AllowedToGoOut)
        }
    }

    fun saveImageUri(uri: URI) {
        coverImageUrl = uri.toString()
    }

    fun onBackPressed() {

        if (coverImageUrl.isNotEmpty() || playlistName.isNotEmpty() || playlistDescription.isNotEmpty()) {
            _screenStateFlow.tryEmit(ScreenState.NeedsToAsk)
        } else {
            _screenStateFlow.tryEmit(ScreenState.AllowedToGoOut)
        }
    }
}