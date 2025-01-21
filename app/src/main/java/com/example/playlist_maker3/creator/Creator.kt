package com.example.playlist_maker3.creator


import android.content.Context

import com.example.playlist_maker3.search.data.TrackRepositoryImpl
import com.example.playlist_maker3.search.network.RetrofitNetworkClient
import com.example.playlist_maker3.settings.domain.repository.DarkTheme
import com.example.playlist_maker3.settings.data.DarkThemeImpl
import com.example.playlist_maker3.search.domain.api.TrackRepository
import com.example.playlist_maker3.search.domain.api.TracksInteractor
import com.example.playlist_maker3.search.domain.impl.TrackInteractorImpl
import com.example.playlist_maker3.search.domain.api.SearchHistoryRepository
import com.example.playlist_maker3.search.data.SearchHistoryRepositoryImpl
import com.example.playlist_maker3.App
import com.example.playlist_maker3.settings.domain.interactor.DarkThemeInteractor
import com.example.playlist_maker3.player.domain.PlaybackControl
import com.example.playlist_maker3.player.domain.impl.PlaybackControlImpl
import com.example.playlist_maker3.player.data.PlayerImpl
import com.example.playlist_maker3.search.domain.api.SearchHistoryInteractor
import com.example.playlist_maker3.settings.domain.impl.DarkThemeInteractorImpl
import com.example.playlist_maker3.search.domain.impl.SearchHistoryInteractorImpl
import com.example.playlist_maker3.sharing.data.ExternalNavigator
import com.example.playlist_maker3.sharing.data.impl.ExternalNavigatorImpl
import com.example.playlist_maker3.sharing.domain.ShareInteractor
import com.example.playlist_maker3.sharing.domain.ShareInteractorImpl


object Creator {
    val context: Context = App.applicationContext()
    val app = App()

    private fun provideExternalNavigator(): ExternalNavigator {
        return ExternalNavigatorImpl(context)
    }

    fun provideShareInteractor(): ShareInteractor {
        return ShareInteractorImpl(provideExternalNavigator())
    }

    fun provideTrackInteractor(): TracksInteractor {
        return TrackInteractorImpl(getTrackRepository())
    }

    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TrackInteractorImpl(getTrackRepository())
    }

    fun createPlayControl(): PlaybackControl {
        return PlaybackControlImpl(PlayerImpl())
    }

    private fun provideDarkTheme(): DarkTheme {
        return DarkThemeImpl(context, app)
    }

    fun provideDarkThemeInteractor(): DarkThemeInteractor {
        return DarkThemeInteractorImpl(provideDarkTheme())
    }

    private fun provideSearchHistoryRepository(): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(context)
    }

    fun provideSearchHistoryInteractor(): SearchHistoryInteractor {
        return SearchHistoryInteractorImpl(provideSearchHistoryRepository())
    }

}