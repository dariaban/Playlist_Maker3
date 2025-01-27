package com.example.playlist_maker3.search.data.mapper

import com.example.playlist_maker3.search.data.dto.TracksDto
import com.example.playlist_maker3.search.domain.model.Track

class TrackMapper() {
    fun trackMap(track: TracksDto): Track {
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
            track.primaryGenreName
        )
    }
}