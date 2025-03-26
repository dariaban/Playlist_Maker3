package com.example.playlist_maker3.media.data

import com.example.playlist_maker3.media.data.converters.TrackDbConvertor
import com.example.playlist_maker3.media.data.db.AppDatabase
import com.example.playlist_maker3.media.data.db.entity.TrackEntity
import com.example.playlist_maker3.media.domain.db.FavoriteRepository
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(
    private val database: AppDatabase,
    private val trackDbConvertor: TrackDbConvertor
) : FavoriteRepository {
    override fun historyTracks(): Flow<List<Track>> = flow{
        val tracks = database.trackDao().getTracks()
        emit(convertFromTrackEntity(tracks))
    }

    override fun isChecked(): Flow<List<Int>> = flow {
        val tracksId = database.trackDao().getId()
        emit(tracksId)
    }

    override suspend fun addTrack(track: Track) {
        database.trackDao().insertTracks(convertToTrackEntity(track))
    }

    override suspend fun deleteTrack(track: Track) {
        database.trackDao().deleteTrack(convertToTrackEntity(track))
    }

    private fun convertFromTrackEntity(tracks: List<TrackEntity>): List<Track>{
        return tracks.map{ track -> trackDbConvertor.map(track)}
    }

    private fun convertToTrackEntity(track: Track): TrackEntity {
        return trackDbConvertor.map(track)
    }
}