package com.example.playlist_maker3.search.data.dto

class TrackSearchResponse(
    val resultCount: Int,
    val expression: String,
    val results: List<TracksDto>
) : Response()
