package com.example.playlist_maker3

import android.content.Context
import com.example.playlist_maker3.data.Impl.TrackRepositoryImpl
import com.example.playlist_maker3.data.network.RetrofitNetworkClient
import com.example.playlist_maker3.data.repository.DarkTheme
import com.example.playlist_maker3.domain.api.TrackRepository
import com.example.playlist_maker3.domain.api.TracksInteractor
import com.example.playlist_maker3.domain.impl.TrackInteractorImpl
import com.example.playlist_maker3.data.repository.Player
import com.example.playlist_maker3.data.repository.SearchHistoryRepository
import com.example.playlist_maker3.presentation.PlayerPresenter
import com.example.playlist_maker3.domain.api.PlaybackControlImpl
import com.example.playlist_maker3.domain.use_case.DarkThemeInteractor
import com.example.playlist_maker3.domain.use_case.HistoryInteractor


object Creator {
    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TrackInteractorImpl(getTrackRepository())
    }

    fun createPlayControl(playerPresenter: PlayerPresenter): PlaybackControlImpl {
        return PlaybackControlImpl(Player(), playerPresenter)
    }
}