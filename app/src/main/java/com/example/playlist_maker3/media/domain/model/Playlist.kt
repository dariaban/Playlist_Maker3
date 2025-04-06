package com.example.playlist_maker3.media.domain.model

import com.example.playlist_maker3.search.domain.model.Track


data class Playlist(
    val id: Int,
    val coverImageUrl: String,
    val playlistName: String,
    val playlistDescription:String,
    var trackList: List<Track>,
    var tracksCount: Int
)