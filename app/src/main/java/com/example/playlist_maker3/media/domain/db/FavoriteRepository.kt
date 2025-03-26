package com.example.playlist_maker3.media.domain.db

import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    fun historyTracks(): Flow<List<Track>>

    suspend fun addTrack(track: Track)

    suspend fun deleteTrack(track: Track)

    fun isChecked(): Flow<List<Int>>
}