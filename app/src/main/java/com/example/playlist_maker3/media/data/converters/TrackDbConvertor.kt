package com.example.playlist_maker3.media.data.converters

import com.example.playlist_maker3.media.data.db.entity.TrackEntity
import com.example.playlist_maker3.search.data.dto.TracksDto
import com.example.playlist_maker3.search.domain.model.Track

class TrackDbConvertor {
    fun map(track: Track): TrackEntity {
        return TrackEntity(
            track.trackId,
            track.artistName,
            track.collectionName,
            track.previewUrl?:"",
            track.artworkUrl100,
            track.trackName,
            track.releaseDate?:"",
            track.trackTimeMillis,
            track.country,
            track.primaryGenreName,
            track.saveDate

        )
    }
    fun mapToTrack(track: TrackEntity): Track{
        return Track(
            track.trackId,
            track.artistName,
            track.collectionName,
            track.previewUrl,
            track.artworkUrl100,
            track.trackName,
            track.releaseDate,
            track.trackTimeMillis,
            track.country,
            track.primaryGenreName,
            track.saveDate
        )
    }
}