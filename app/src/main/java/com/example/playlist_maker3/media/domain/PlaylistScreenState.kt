package com.example.playlist_maker3.media.domain

import com.example.playlist_maker3.media.domain.model.Playlist

sealed class PlaylistsScreenState {
    data object Empty : PlaylistsScreenState()
    class Content(val playlists: List<Playlist>) : PlaylistsScreenState()
}
