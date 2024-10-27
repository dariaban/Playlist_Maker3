package com.example.playlist_maker3
import android.content.SharedPreferences
import com.example.playlist_maker3.data.DarkThemeImpl
import com.example.playlist_maker3.data.Impl.TrackRepositoryImpl
import com.example.playlist_maker3.domain.api.SearchHistoryRepository
import com.example.playlist_maker3.data.network.RetrofitNetworkClient
import com.example.playlist_maker3.domain.api.TrackRepository
import com.example.playlist_maker3.domain.api.TracksInteractor
import com.example.playlist_maker3.data.Impl.SearchHistoryRepositoryImpl
import com.example.playlist_maker3.domain.impl.TrackInteractorImpl
import com.example.playlist_maker3.data.Player
import com.example.playlist_maker3.domain.PlayerPresenter
import com.example.playlist_maker3.domain.api.DarkTheme
import com.example.playlist_maker3.domain.api.PlaybackControlImpl


object Creator {
    private fun getTrackRepository(): TrackRepository {
        return TrackRepositoryImpl(RetrofitNetworkClient())
    }
    fun provideTracksInteractor(): TracksInteractor {
        return TrackInteractorImpl(getTrackRepository())
    }
    fun provideSearchHistoryRepository(sharedPreferences: SharedPreferences): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(sharedPreferences)
    }
    fun createPlayControl(playerPresenter: PlayerPresenter): PlaybackControlImpl {
        return PlaybackControlImpl(Player(), playerPresenter)
    }
    fun provideDarkTheme(themePreferences: SharedPreferences): DarkTheme{
        return DarkThemeImpl(themePreferences)
    }
}