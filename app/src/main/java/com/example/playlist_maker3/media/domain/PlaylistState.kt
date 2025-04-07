package com.example.playlist_maker3.media.domain

import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.search.domain.model.Track

sealed interface PlaylistState {

    data class PlaylistInfo(
        val playlist: Playlist
    ) : PlaylistState

    data class PlaylistTracks(
        val tracks: List<Track>
    ) : PlaylistState

}