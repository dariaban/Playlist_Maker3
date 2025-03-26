package com.example.playlist_maker3.di

import android.content.Context.MODE_PRIVATE
import android.media.MediaPlayer
import androidx.room.Room
import com.example.playlist_maker3.media.data.db.AppDatabase
import com.example.playlist_maker3.player.data.PlayerImpl
import com.example.playlist_maker3.search.data.network.ItunesApiService
import com.example.playlist_maker3.search.data.network.NetworkClient
import com.example.playlist_maker3.search.data.network.RetrofitNetworkClient
import com.example.playlist_maker3.search.data.repository.SearchHistoryRepositoryImpl
import com.example.playlist_maker3.search.data.repository.SearchHistoryRepositoryImpl.Companion.HISTORY_PREFERENCES
import com.example.playlist_maker3.search.domain.api.SearchHistoryRepository
import com.example.playlist_maker3.settings.data.DarkThemeImpl
import com.example.playlist_maker3.settings.domain.repository.DarkTheme
import com.example.playlist_maker3.sharing.data.ExternalNavigator
import com.example.playlist_maker3.sharing.data.impl.ExternalNavigatorImpl
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<ItunesApiService> {
        Retrofit.Builder().baseUrl("https://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ItunesApiService::class.java)
    }

    factory { Gson() }
    factory { MediaPlayer() }

    factory { PlayerImpl(get()) }
    single {
        androidContext().getSharedPreferences(
            HISTORY_PREFERENCES,
            MODE_PRIVATE
        )
    }

    single<SearchHistoryRepository> {
        SearchHistoryRepositoryImpl(get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient()
    }
    single<DarkTheme> { DarkThemeImpl(context = get()) }

    factory<ExternalNavigator> { ExternalNavigatorImpl(context = get()) }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db").build()
    }
}