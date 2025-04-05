package com.example.playlist_maker3.media.domain

import com.example.playlist_maker3.media.domain.model.Playlist

sealed class BottomSheetState(
    val content: List<Playlist> = emptyList(),
) {
    data object Empty: BottomSheetState()
    data class AddedNow(val playlistModel: Playlist): BottomSheetState()
    data class AddedAlready(val playlistModel: Playlist): BottomSheetState()
    data class Content(val playlists: List<Playlist>): BottomSheetState(content = playlists)
}