package com.example.playlist_maker3.search.domain.model

data class Result(
    val resultCount: Int,
    val results: List<Track>
)