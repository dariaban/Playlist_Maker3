package com.example.playlist_maker3.media.domain

import com.example.playlist_maker3.search.domain.model.Track

sealed interface FavoriteState {
    data object Loading : FavoriteState

    data class Content(
        val tracks : List<Track>
    ): FavoriteState

    data class Empty(
        val message: String
    ): FavoriteState
}