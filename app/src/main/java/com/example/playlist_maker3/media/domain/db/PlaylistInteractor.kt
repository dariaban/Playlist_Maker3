package com.example.playlist_maker3.media.domain.db

import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface PlaylistsInteractor {
    fun getPlaylists(): Flow<List<Playlist>>
    fun isTrackAlreadyExists(playlist: Playlist, track: Track): Boolean
    suspend fun addTrackToPlaylist(playlist: Playlist, track: Track)
}