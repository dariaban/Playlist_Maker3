package com.example.playlist_maker3.di

import com.example.playlist_maker3.player.ui.PlayerViewModel
import com.example.playlist_maker3.search.ui.SearchViewModel
import com.example.playlist_maker3.settings.ui.SettingsActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PlayerViewModel(get())
    }

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        SettingsActivityViewModel(get(), get())
    }
}