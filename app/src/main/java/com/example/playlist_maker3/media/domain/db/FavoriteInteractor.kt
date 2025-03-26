package com.example.playlist_maker3.media.domain.db

import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteInteractor {
    fun historyTracks(): Flow<List<Track>>

    suspend fun updateFavorite(track: Track): Boolean

    suspend fun isChecked(tracksId: Int): Boolean
}