package com.example.playlist_maker3.media.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlists_track")
class TrackPlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val playlistId: Int,
    val trackId: Int
)
