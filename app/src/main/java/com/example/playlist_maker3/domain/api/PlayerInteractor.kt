package com.example.playlist_maker3.domain.api

import com.example.playlist_maker3.domain.models.Track

interface PlayerInteractor {

    fun preparePlayer(track: Track, prepareCallback: () -> Unit, completeCallback: () -> Unit)
    fun start()
    fun pause()
    fun getCurrentPosition(): Int
    fun release()
}