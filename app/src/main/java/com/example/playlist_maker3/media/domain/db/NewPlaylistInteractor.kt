package com.example.playlist_maker3.media.domain.db

import com.example.playlist_maker3.media.domain.model.Playlist

interface NewPlaylistInteractor {
    suspend fun create(playlist: Playlist)
}