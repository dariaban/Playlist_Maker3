package com.example.playlist_maker3.media.domain.impl

import android.net.Uri
import com.example.playlist_maker3.media.domain.db.PlaylistsInteractor
import com.example.playlist_maker3.media.domain.db.PlaylistsRepository
import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.search.domain.model.Track

class PlaylistsInteractorImpl(
    private val repository: PlaylistsRepository,
) : PlaylistsInteractor {
    override suspend fun createPlaylist(playlistName: String, playlistDescription: String, imageUri: Uri?) =
        repository.createPlaylist(playlistName, playlistDescription, imageUri)

    override suspend fun addTrack(track: Track, playlistId: Int) =
        repository.addTrack(track, playlistId)

    override suspend fun isTrackAlreadyExists(trackId : Int, playlistId: Int) : Boolean=
        repository.isTrackAlreadyExists(trackId, playlistId)

    override suspend fun getPlaylists(): List<Playlist> =
        repository.getPlaylists()

    override suspend fun getPlaylist(playlistId: Int) : Playlist =
        repository.getPlaylist(playlistId)

    override suspend fun getPlaylistTracks(playlistId: Int): List<Track> =
        repository.getPlaylistTracks(playlistId)

    override suspend fun updatePlaylist(playlistId: Int, playlistName: String, playlistDescription: String, imageUri: Uri?) {
        repository.updatePlaylist(playlistId, playlistName, playlistDescription, imageUri)
    }

    override suspend fun deleteTrack(trackId: Int, playlistId: Int) =
        repository.deleteTrack(trackId, playlistId)

    override suspend fun deletePlaylist(playlist: Playlist) =
        repository.deletePlaylist(playlist)

}
