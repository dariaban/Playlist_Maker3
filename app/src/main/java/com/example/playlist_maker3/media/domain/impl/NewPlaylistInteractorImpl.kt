package com.example.playlist_maker3.media.domain.impl

import com.example.playlist_maker3.media.domain.db.NewPlaylistInteractor
import com.example.playlist_maker3.media.domain.db.PlaylistsRepository
import com.example.playlist_maker3.media.domain.model.Playlist

class NewPlaylistInteractorImpl (
    private val repository: PlaylistsRepository,
) : NewPlaylistInteractor {

    override suspend fun create(playlist: Playlist) {
        repository.createPlaylist(playlist)
    }
}