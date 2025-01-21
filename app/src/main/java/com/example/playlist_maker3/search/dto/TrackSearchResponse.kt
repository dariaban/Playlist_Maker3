package com.example.playlist_maker3.search.dto

class TrackSearchResponse(
    val resultCount: Int,
    val expression: String,
    val results: List<TracksDto>
) : Response()
