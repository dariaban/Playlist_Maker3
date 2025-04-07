package com.example.playlist_maker3.media.data.converters

import com.example.playlist_maker3.media.data.db.entity.PlaylistWithCountTracks
import com.example.playlist_maker3.media.data.db.entity.PlaylistsTrackEntity
import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.search.domain.model.Track

class PlaylistDbConvertor {
    fun map(playlistWithCountTracks: PlaylistWithCountTracks): Playlist {
        playlistWithCountTracks.apply {
            return Playlist(
                playlistId!!,
                name,
                description,
                cover,
                tracksCount,
            )
        }
    }

    fun map(playListsTrackEntity: PlaylistsTrackEntity): Track {
        playListsTrackEntity.apply {
            return Track(
                trackId,
                artistName,
                collectionName,
                previewUrl,
                artworkUrl100,
                artworkUrl60,
                trackName,
                releaseDate,
                trackTimeMillis,
                country,
                primaryGenreName,
            )
        }
    }

    fun map(track: Track): PlaylistsTrackEntity {
        track.apply {
            return PlaylistsTrackEntity(
                trackId,
                artistName,
                collectionName,
                previewUrl,
                artworkUrl100,
                artworkUrl60,
                trackName,
                releaseDate,
                trackTimeMillis,
                country,
                primaryGenreName
            )
        }
    }
}
