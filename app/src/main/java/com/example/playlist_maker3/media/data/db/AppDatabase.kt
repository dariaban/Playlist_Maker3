package com.example.playlist_maker3.media.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlist_maker3.media.data.db.dao.TrackDao
import com.example.playlist_maker3.media.data.db.entity.TrackEntity

@Database(
    version = 3, entities = [TrackEntity::class],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao
}