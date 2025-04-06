package com.example.playlist_maker3.media.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlist_maker3.media.data.db.dao.PlaylistDao
import com.example.playlist_maker3.media.data.db.dao.TrackDao
import com.example.playlist_maker3.media.data.db.entity.PlaylistEntity
import com.example.playlist_maker3.media.data.db.entity.TrackEntity

@Database(
    version = 12, entities = [TrackEntity::class, PlaylistEntity::class],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao

    abstract fun playlistsDao(): PlaylistDao
}