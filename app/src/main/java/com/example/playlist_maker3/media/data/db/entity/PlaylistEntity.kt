package com.example.playlist_maker3.media.data.db.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val playlistId: Int?,
    val name: String,
    val description: String,
    val cover: String?,
)

data class PlaylistWithCountTracks(
    val playlistId: Int?,
    val name: String,
    val description: String,
    val cover: String?,
    val tracksCount: Int,
)



