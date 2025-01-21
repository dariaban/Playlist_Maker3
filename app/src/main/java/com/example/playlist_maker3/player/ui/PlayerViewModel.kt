package com.example.playlist_maker3.player.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlist_maker3.creator.Creator
import com.example.playlist_maker3.player.domain.PlaybackControl
import com.example.playlist_maker3.search.domain.model.Track

class PlayerViewModel(private val playerInteractor: PlaybackControl) :
    ViewModel() {

    companion object {
        private const val DELAY_MILLIS = 25L

        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val interactor = Creator.createPlayControl()
                PlayerViewModel(
                    interactor
                )
            }
        }
    }

    init {
        playerInteractor.setOnStateChangeListener { state ->
            stateLiveData.postValue(state)
            val progressTime = playerInteractor.createUpdateProgressTime()
            stateProgressTimeLiveData.postValue(progressTime)
            if (state == PlayerState.STATE_PREPARED) mainThreadHandler.removeCallbacks(
                progressTimeRunnable
            )
        }
    }

    private val stateLiveData = MutableLiveData<PlayerState>()
    fun observeState(): LiveData<PlayerState> = stateLiveData

    private val stateProgressTimeLiveData = MutableLiveData<String>()
    fun observeProgressTimeState(): LiveData<String> = stateProgressTimeLiveData


    private var mainThreadHandler = Handler(Looper.getMainLooper())
    private val progressTimeRunnable = object : Runnable {
        override fun run() {
            val progressTime = playerInteractor.createUpdateProgressTime()
            stateProgressTimeLiveData.postValue(progressTime)
            mainThreadHandler.postDelayed(this, DELAY_MILLIS)
        }
    }

    fun prepare(track: Track) {
        playerInteractor.prepare(track)
        renderState(PlayerState.STATE_PREPARED)
    }

    fun playbackControl() {
        val state = playerInteractor.playbackControl()
        renderState(state)
        if (state == PlayerState.STATE_PLAYING) mainThreadHandler.post(progressTimeRunnable) else mainThreadHandler.removeCallbacks(
            progressTimeRunnable
        )
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.release()
        mainThreadHandler.removeCallbacks(progressTimeRunnable)
        renderState(PlayerState.STATE_COMPLETED)
    }

    fun onPause() {
        playerInteractor.pause()
        renderState(PlayerState.STATE_PAUSED)
    }

    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
    }
}