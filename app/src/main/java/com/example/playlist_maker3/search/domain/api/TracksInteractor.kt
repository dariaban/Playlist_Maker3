package com.example.playlist_maker3.search.domain.api

import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow

interface TracksInteractor {
    suspend fun searchTracks(expression: String): Flow<Pair<List<Track>?, String?>>

}