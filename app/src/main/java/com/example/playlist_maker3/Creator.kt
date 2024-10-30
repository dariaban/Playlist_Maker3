package com.example.playlist_maker3

import android.app.Application
import android.content.Context

import com.example.playlist_maker3.data.repository.TrackRepositoryImpl
import com.example.playlist_maker3.data.network.RetrofitNetworkClient
import com.example.playlist_maker3.data.repository.DarkTheme
import com.example.playlist_maker3.data.repository.DarkThemeImpl
import com.example.playlist_maker3.domain.api.TrackRepository
import com.example.playlist_maker3.domain.api.TracksInteractor
import com.example.playlist_maker3.domain.impl.TrackInteractorImpl
import com.example.playlist_maker3.data.repository.Player
import com.example.playlist_maker3.data.repository.SearchHistoryRepository
import com.example.playlist_maker3.data.repository.SearchHistoryRepositoryImpl
import com.example.playlist_maker3.domain.App
import com.example.playlist_maker3.domain.api.DarkThemeInteractor
import com.example.playlist_maker3.presentation.PlayerPresenter
import com.example.playlist_maker3.domain.api.PlaybackControlImpl
import com.example.playlist_maker3.domain.api.SearchHistoryInteractor
import com.example.playlist_maker3.domain.impl.DarkThemeInteractorImpl
import com.example.playlist_maker3.domain.impl.SearchHistoryInteractorImpl


object Creator {
    val context: Context = App.applicationContext()

    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TrackInteractorImpl(getTrackRepository())
    }

    fun createPlayControl(playerPresenter: PlayerPresenter): PlaybackControlImpl {
        return PlaybackControlImpl(Player(), playerPresenter)
    }

    private fun provideDarkTheme(): DarkTheme {
        return DarkThemeImpl(context)
    }

    fun provideDarkThemeInteractor(): DarkThemeInteractor {
        return DarkThemeInteractorImpl(provideDarkTheme())
    }

    private fun provideSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(context)
    }

    fun provideSearhHistoryInteractor(): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(provideSearchHistoryRepository())
    }

}