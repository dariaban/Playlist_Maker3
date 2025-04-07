package com.example.playlist_maker3.media.domain

import com.example.playlist_maker3.media.domain.model.Playlist

sealed interface PlaylistsState {
    data object Empty : PlaylistsState
    data class Playlists(
        val playlists: List<Playlist>
    ) : PlaylistsState

    data class AddTrackResult(
        val isAdded: Boolean,
        val playlistName: String
    ) : PlaylistsState
}
