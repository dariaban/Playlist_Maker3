package com.example.playlist_maker3.media.data.converters

import com.example.playlist_maker3.media.data.db.entity.PlaylistEntity
import com.example.playlist_maker3.media.domain.model.Playlist
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Calendar


class PlaylistDbConvertor {
    fun map(playlist: Playlist): PlaylistEntity {
        return with(playlist) {
            PlaylistEntity(
                id = id,
                playlistName = playlistName,
                playlistDescription = playlistDescription,
                imageUrl = coverImageUrl,
                trackList = Json.encodeToString(trackList),
                countTracks = tracksCount,
                Calendar.getInstance().timeInMillis,
            )
        }
    }
    fun map(playlist: PlaylistEntity): Playlist {

        return with(playlist) {
            Playlist(
                id = id,
                coverImageUrl = imageUrl,
                playlistName = playlistName,
                playlistDescription = playlistDescription,
                trackList = Json.decodeFromString(trackList),
                tracksCount = countTracks,
            )
        }
    }
}
