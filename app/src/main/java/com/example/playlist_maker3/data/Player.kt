package com.example.playlist_maker3.data

import android.media.MediaPlayer
import com.example.playlist_maker3.domain.api.PlayerInteractor
import com.example.playlist_maker3.domain.models.Track

class Player : PlayerInteractor {

    private var player = MediaPlayer()

    override fun preparePlayer(
        track: Track,
        prepareCallback: () -> Unit,
        completeCallback: () -> Unit
    ) {

        player.setDataSource(track.previewUrl)
        player.prepareAsync()
        player.setOnPreparedListener {
            prepareCallback()
        }
        player.setOnCompletionListener {
            completeCallback()
        }
    }

    override fun start() {
        player.start()
    }

    override fun pause() {
        player.pause()
    }

    override fun getCurrentPosition(): Int {
        return player.currentPosition
    }

    override fun release() {
        player.release()
    }
}