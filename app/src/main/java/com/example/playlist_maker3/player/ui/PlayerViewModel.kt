package com.example.playlist_maker3.player.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlist_maker3.player.domain.PlaybackControl
import com.example.playlist_maker3.player.domain.PlayerState
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(private val playerInteractor: PlaybackControl) :
    ViewModel() {

    companion object {
        private const val DELAY_MILLIS = 25L

    }

    init {
        playerInteractor.setOnStateChangeListener { state ->
            stateLiveData.postValue(state)
            val progressTime = playerInteractor.createUpdateProgressTime()
            stateProgressTimeLiveData.postValue(progressTime)
            if (state == PlayerState.STATE_PREPARED) timerJob?.cancel()
        }
    }

    private val stateLiveData = MutableLiveData<PlayerState>()
    fun observeState(): LiveData<PlayerState> = stateLiveData

    private val stateProgressTimeLiveData = MutableLiveData<String>()
    fun observeProgressTimeState(): LiveData<String> = stateProgressTimeLiveData


    private var timerJob: Job? = null


    fun prepare(track: Track) {
        playerInteractor.prepare(track)
        renderState(PlayerState.STATE_PREPARED)
    }

    fun playbackControl() {
        val state = playerInteractor.playbackControl()
        renderState(state)
        if (state == PlayerState.STATE_PLAYING) startTimer(state) else timerJob?.cancel()

    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.release()
        timerJob?.cancel()
        renderState(PlayerState.STATE_COMPLETED)
    }

    fun onPause() {
        playerInteractor.pause()
        renderState(PlayerState.STATE_PAUSED)
    }

    private fun renderState(state: PlayerState) {
        stateLiveData.postValue(state)
    }

    private fun startTimer(state: PlayerState) {
        timerJob = viewModelScope.launch {
            while (state == PlayerState.STATE_PLAYING) {
                val progressTime = playerInteractor.createUpdateProgressTime()
                delay(DELAY_MILLIS)
                stateProgressTimeLiveData.postValue(progressTime)
            }
        }
    }
}