package com.example.playlist_maker3.media.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track_table")
data class TrackEntity(
    @PrimaryKey
    val trackId: Int,
    val artistName: String,
    val collectionName: String,
    val previewUrl: String,
    val artworkUrl100: String,
    val trackName: String,
    val releaseDate: String,
    val trackTimeMillis: Int,
    val country: String,
    val primaryGenreName: String,
    var saveDate: Long
)
