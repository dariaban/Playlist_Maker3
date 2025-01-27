package com.example.playlist_maker3.player.data

import android.media.MediaPlayer
import com.example.playlist_maker3.player.domain.Player
import com.example.playlist_maker3.player.domain.PlayerState
import com.example.playlist_maker3.search.domain.model.Track

class PlayerImpl(private val mediaPlayer: MediaPlayer) : Player {
    private var stateCallback: ((PlayerState) -> Unit)? = null

    override fun prepare(track: Track) {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            stateCallback?.invoke(PlayerState.STATE_PREPARED)
        }
        mediaPlayer.setOnCompletionListener {
            stateCallback?.invoke(PlayerState.STATE_PREPARED)
        }
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun setOnStateChangeListener(callback: (PlayerState) -> Unit) {
        stateCallback = callback
    }
}