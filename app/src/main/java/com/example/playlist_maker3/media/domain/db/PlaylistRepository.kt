package com.example.playlist_maker3.media.domain.db

import com.example.playlist_maker3.media.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    suspend fun createPlaylist(playlist: Playlist)

    suspend fun deletePlaylist(playlist: Playlist)

    suspend fun updateTracks(playlist: Playlist)

    fun getSavedPlaylists(): Flow<List<Playlist>>
}

