package com.example.playlist_maker3.domain.api

import com.example.playlist_maker3.domain.models.Track

interface PlaybackControl {
    fun playbackControl()
    fun prepare(track: Track)
    fun pause()
    fun createUpdateProgressTimeRunnable(): Runnable
    fun release()
}
