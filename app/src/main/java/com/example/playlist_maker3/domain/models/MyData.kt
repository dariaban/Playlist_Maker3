package com.example.playlist_maker3.domain.models

data class Result(
    val resultCount: Int,
    val results: List<Track>
)