package com.example.playlist_maker3.search.domain.impl


import com.example.playlist_maker3.search.domain.api.Resource
import com.example.playlist_maker3.search.domain.api.TrackRepository
import com.example.playlist_maker3.search.domain.api.TracksInteractor
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.Executors

class TrackInteractorImpl(private val repository: TrackRepository) : TracksInteractor {
    override suspend fun searchTracks(expression: String): Flow<Pair<List<Track>?, String?>> {
        return repository.searchTracks(expression).map { result ->
            when (result) {
                is Resource.Error -> {
                    Pair(null, result.message)
                }
                is Resource.Success -> {
                    Pair(result.data, null)
                }
            }
        }
    }
}
