package com.example.playlist_maker3.di

import com.example.playlist_maker3.media.domain.db.FavoriteInteractor
import com.example.playlist_maker3.media.domain.db.NewPlaylistInteractor
import com.example.playlist_maker3.media.domain.db.PlaylistsInteractor
import com.example.playlist_maker3.media.domain.impl.FavoriteInteractorImpl
import com.example.playlist_maker3.media.domain.impl.NewPlaylistInteractorImpl
import com.example.playlist_maker3.media.domain.impl.PlaylistsInteractorImpl
import com.example.playlist_maker3.player.domain.PlaybackControl
import com.example.playlist_maker3.player.domain.impl.PlaybackControlImpl
import com.example.playlist_maker3.search.domain.api.SearchHistoryInteractor
import com.example.playlist_maker3.search.domain.api.TracksInteractor
import com.example.playlist_maker3.search.domain.impl.SearchHistoryInteractorImpl
import com.example.playlist_maker3.search.domain.impl.TrackInteractorImpl
import com.example.playlist_maker3.settings.domain.impl.DarkThemeInteractorImpl
import com.example.playlist_maker3.settings.domain.interactor.DarkThemeInteractor
import com.example.playlist_maker3.sharing.domain.ShareInteractor
import com.example.playlist_maker3.sharing.domain.ShareInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    single<TracksInteractor> {
        TrackInteractorImpl(get())
    }

    single<DarkThemeInteractor> {
        DarkThemeInteractorImpl(get())
    }
    single<SearchHistoryInteractor> { SearchHistoryInteractorImpl(get()) }

    single<ShareInteractor> {
        ShareInteractorImpl(get())
    }

    factory<PlaybackControl> {
        PlaybackControlImpl(get())
    }

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single<PlaylistsInteractor> { PlaylistsInteractorImpl(get()) }
    single<NewPlaylistInteractor>{NewPlaylistInteractorImpl(get())}
}