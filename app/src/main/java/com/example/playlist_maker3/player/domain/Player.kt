package com.example.playlist_maker3.player.domain

import com.example.playlist_maker3.search.domain.model.Track
import com.example.playlist_maker3.player.ui.PlayerState

interface Player {
    fun prepare(track: Track)
    fun start()
    fun pause()
    fun getCurrentPosition(): Int
    fun release()
    fun setOnStateChangeListener(callback: (PlayerState) -> Unit)
}