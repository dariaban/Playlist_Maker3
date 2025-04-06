package com.example.playlist_maker3.media.data

import android.util.Log
import com.example.playlist_maker3.media.data.converters.PlaylistDbConvertor
import com.example.playlist_maker3.media.data.db.AppDatabase
import com.example.playlist_maker3.media.data.db.entity.PlaylistEntity
import com.example.playlist_maker3.media.domain.db.PlaylistsRepository
import com.example.playlist_maker3.media.domain.model.Playlist
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistsRepositoryImpl(
    private val database: AppDatabase,
    private val converter: PlaylistDbConvertor,
) : PlaylistsRepository {

    override suspend fun createPlaylist(playlist: Playlist) {
        val playlistEntity = converter.map(playlist)
        database
            .playlistsDao()
            .insertPlaylist(playlistEntity)
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        database
            .playlistsDao()
            .deletePlaylist(converter.map(playlist))
    }

    override suspend fun updateTracks(playlist: Playlist) {
        database
            .playlistsDao()
            .updatePlaylist(converter.map(playlist))
    }

    override fun getSavedPlaylists(): Flow<List<Playlist>> {
        return database
            .playlistsDao()
            .getSavedPlaylists()
            .map { convertFromTrackEntity(it) }
    }

    private fun convertFromTrackEntity(playlists: List<PlaylistEntity>): List<Playlist> {
        return playlists.map { converter.map(it) }
    }
}



