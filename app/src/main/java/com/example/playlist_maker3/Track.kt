package com.example.playlist_maker3

data class Track(
    val trackId: Int,
    val artistName: String,
    val artworkUrl100: String,
    val trackName: String,
    val trackTimeMillis: Int,
) {

}