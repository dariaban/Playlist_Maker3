package com.example.playlist_maker3.domain.api

import com.example.playlist_maker3.domain.PlayerPresenter
import com.example.playlist_maker3.domain.TimeFormatter
import com.example.playlist_maker3.domain.models.Track

class PlaybackControlImpl(val mediaPlayer: PlayerInteractor, val playerPresenter: PlayerPresenter) :
    PlaybackControl {


    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }

    private var playerState = STATE_DEFAULT

    override fun prepare(track: Track) {
        mediaPlayer.preparePlayer(track, this::setPlayerStatePrepared, this::setPlayerState)
    }


    private fun setPlayerState() {
        playerState = STATE_PREPARED
    }

    private fun setPlayerStatePrepared() {
        playerState = STATE_PREPARED
        playerPresenter.playButtonEnabled()
    }

    override fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pause()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                start()
            }
        }
    }

        private fun start() {
            mediaPlayer.start()
            playerPresenter.startPlayer()
            playerState = STATE_PLAYING
        }

        override fun pause() {
            mediaPlayer.pause()
            playerPresenter.pausePlayer()
            playerState = STATE_PAUSED
        }

    override fun createUpdateProgressTimeRunnable(): Runnable {
        return object : Runnable {
            override fun run() {

                when (playerState) {
                    STATE_PLAYING -> {
                        playerPresenter.progressTimeViewUpdate(TimeFormatter.format(mediaPlayer.getCurrentPosition()))
                        playerPresenter.postDelayed(this)
                    }
                    STATE_PAUSED -> {
                        playerPresenter.removeCallbacks(this)
                    }
                    STATE_PREPARED -> {
                        playerPresenter.pausePlayer()
                        playerPresenter.progressTimeViewUpdate(TimeFormatter.ZERO_TIME)
                        playerPresenter.removeCallbacks(this)
                    }
                }
            }
        }
    }


    override fun release() {
        mediaPlayer.release()
    }
}