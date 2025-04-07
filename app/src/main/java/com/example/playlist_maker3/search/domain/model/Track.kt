package com.example.playlist_maker3.search.domain.model

import java.io.Serializable

data class Track(
    val trackId: Int,
    val artistName: String,
    val collectionName: String,
    val previewUrl: String?,
    val artworkUrl100: String?,
    val artworkUrl60: String?,
    val trackName: String,
    val releaseDate: String?,
    val trackTimeMillis: Long,
    val country: String,
    val primaryGenreName: String
): Serializable

