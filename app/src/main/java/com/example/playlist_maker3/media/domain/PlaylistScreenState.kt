package com.example.playlist_maker3.media.domain

import com.example.playlist_maker3.media.domain.model.Playlist

sealed interface PlaylistsScreenState {
    data object Empty : PlaylistsScreenState
    data class NotEmpty(
        val playlists: List<Playlist>
    ) : PlaylistsScreenState
}
