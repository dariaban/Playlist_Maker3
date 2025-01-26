package com.example.playlist_maker3.di


import com.example.playlist_maker3.player.util.TimeFormatter
import com.example.playlist_maker3.search.data.repository.SearchHistoryRepositoryImpl
import com.example.playlist_maker3.search.data.repository.TrackRepositoryImpl
import com.example.playlist_maker3.search.domain.api.SearchHistoryRepository
import com.example.playlist_maker3.search.domain.api.TrackRepository
import com.example.playlist_maker3.settings.data.DarkThemeImpl
import com.example.playlist_maker3.settings.domain.repository.DarkTheme
import org.koin.dsl.module

val repositoryModule = module {

    single { TimeFormatter }

    single<TrackRepository> {
        TrackRepositoryImpl(get())
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }

    single <DarkTheme> {
        DarkThemeImpl(get())
    }


}
