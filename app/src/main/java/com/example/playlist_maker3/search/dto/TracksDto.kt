package com.example.playlist_maker3.search.dto

import java.io.Serializable

data class TracksDto(
    val trackId: Int,
    val artistName: String,
    val collectionName: String,
    val previewUrl: String,
    val artworkUrl100: String,
    val trackName: String,
    val releaseDate: String,
    val trackTimeMillis: Int,
    val country: String,
    val primaryGenreName: String
) : Serializable
