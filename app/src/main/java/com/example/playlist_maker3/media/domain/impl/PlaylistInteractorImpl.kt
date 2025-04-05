package com.example.playlist_maker3.media.domain.impl

import android.util.Log
import com.example.playlist_maker3.media.domain.db.PlaylistsInteractor
import com.example.playlist_maker3.media.domain.db.PlaylistsRepository
import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

class PlaylistsInteractorImpl(
    private val repository: PlaylistsRepository,
) : PlaylistsInteractor {

    override fun getPlaylists(): Flow<List<Playlist>> {
        return repository.getSavedPlaylists()
    }

    override fun isTrackAlreadyExists(playlist: Playlist, track: Track) =
        playlist.trackList.contains(track)

    override suspend fun addTrackToPlaylist(playlist: Playlist, track: Track) {
        playlist.trackList += track
        playlist.tracksCount = playlist.trackList.size
        repository.updateTracks(playlist)
    }
}

