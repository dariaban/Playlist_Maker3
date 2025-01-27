package com.example.playlist_maker3.player.domain.impl

import com.example.playlist_maker3.player.data.PlayerImpl
import com.example.playlist_maker3.player.domain.PlaybackControl
import com.example.playlist_maker3.player.domain.PlayerState
import com.example.playlist_maker3.player.util.TimeFormatter
import com.example.playlist_maker3.search.domain.model.Track

class PlaybackControlImpl(private val mediaPlayer: PlayerImpl) :
    PlaybackControl {


    private var playerState: PlayerState = PlayerState.STATE_DEFAULT

    override fun prepare(track: Track) {
        mediaPlayer.prepare(track)
    }

    override fun playbackControl(): PlayerState {
        when (playerState) {
            PlayerState.STATE_PLAYING -> pause()
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> start()
            else -> {}
        }
        return playerState
    }

    private fun start() {
        mediaPlayer.start()
        playerState = PlayerState.STATE_PLAYING
    }

    override fun pause() {
        mediaPlayer.pause()
        playerState = PlayerState.STATE_PAUSED
    }

    override fun createUpdateProgressTime(): String {
        return if (playerState == PlayerState.STATE_PREPARED) TimeFormatter.ZERO_TIME else TimeFormatter.format(
            mediaPlayer.getCurrentPosition()
        )
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun setOnStateChangeListener(callback: (PlayerState) -> Unit) {
        mediaPlayer.setOnStateChangeListener { state ->
            this.playerState = state
            callback(state)
        }
    }
}

