package com.example.playlist_maker3.media.data.converters

import com.example.playlist_maker3.media.data.db.entity.TrackEntity
import com.example.playlist_maker3.search.domain.model.Track
import java.util.Calendar

class TrackDbConvertor {
    fun map(track: Track): TrackEntity {
        return TrackEntity(
            track.trackId,
            track.artistName,
            track.collectionName,
            track.previewUrl?:"",
            track.artworkUrl100,
            track.artworkUrl60,
            track.trackName,
            track.releaseDate?:"",
            track.trackTimeMillis,
            track.country,
            track.primaryGenreName,
            Calendar.getInstance().timeInMillis
        )
    }
    fun mapToEntity(track: TrackEntity): Track{
        return Track(
            track.trackId,
            track.artistName,
            track.collectionName,
            track.previewUrl,
            track.artworkUrl100,
            track.artworkUrl60,
            track.trackName,
            track.releaseDate,
            track.trackTimeMillis,
            track.country,
            track.primaryGenreName,
        )
    }
}