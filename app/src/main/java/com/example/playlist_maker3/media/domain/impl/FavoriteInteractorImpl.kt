package com.example.playlist_maker3.media.domain.impl

import com.example.playlist_maker3.media.domain.db.FavoriteInteractor
import com.example.playlist_maker3.media.domain.db.FavoriteRepository
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.flow.Flow


class FavoriteInteractorImpl(private val favoriteRepository: FavoriteRepository) : FavoriteInteractor {

    private var isChecked = false

    override fun historyTracks(): Flow<List<Track>> {
        return favoriteRepository.historyTracks()
    }
    override suspend fun updateFavorite(track: Track): Boolean {
        favoriteRepository.isChecked().collect{tracksId ->
            isChecked = if (tracksId.contains(track.trackId)) {
                favoriteRepository.deleteTrack(track)
                false
            } else {
                favoriteRepository.addTrack(track)
                true
            }
        }
        return isChecked
    }

    override suspend fun isChecked(tracksId: Int) : Boolean {
        favoriteRepository.isChecked().collect { id ->
            isChecked = id.contains(tracksId)
        }
        return isChecked
    }
}