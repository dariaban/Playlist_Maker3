package com.example.playlist_maker3.player.domain

import com.example.playlist_maker3.search.domain.model.Track

interface PlaybackControl {
    fun playbackControl(): PlayerState
    fun prepare(track: Track)
    fun pause()
    fun createUpdateProgressTime(): String
    fun release()
    fun setOnStateChangeListener(callback: (PlayerState) -> Unit)
}
