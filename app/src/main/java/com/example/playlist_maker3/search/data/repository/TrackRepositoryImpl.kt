package com.example.playlist_maker3.search.data.repository

import com.example.playlist_maker3.media.data.converters.TrackDbConvertor
import com.example.playlist_maker3.media.data.db.AppDatabase
import com.example.playlist_maker3.search.data.dto.TrackSearchRequest
import com.example.playlist_maker3.search.data.dto.TrackSearchResponse
import com.example.playlist_maker3.search.data.dto.TracksDto
import com.example.playlist_maker3.search.data.mapper.TrackMapper
import com.example.playlist_maker3.search.data.network.NetworkClient
import com.example.playlist_maker3.search.domain.api.Resource
import com.example.playlist_maker3.search.domain.api.TrackRepository
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackMapper: TrackMapper,
    private val appDatabase: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : TrackRepository {

    override suspend fun searchTracks(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        if (response.resultCode == 200) {
            emit(Resource.Success((response as TrackSearchResponse).results.map {
                trackMapper.trackMap(it)
            }))
        } else {
            emit(Resource.Error("Network error with code: ${response.resultCode}"))
        }
    }
}