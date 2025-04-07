package com.example.playlist_maker3.di

import com.example.playlist_maker3.media.ui.FavoritesViewModel
import com.example.playlist_maker3.media.ui.MediaViewModel
import com.example.playlist_maker3.media.ui.NewPlaylistViewModel
import com.example.playlist_maker3.media.ui.PlaylistMenuBottomSheetViewModel
import com.example.playlist_maker3.media.ui.PlaylistViewModel
import com.example.playlist_maker3.media.ui.PlaylistsBottomSheetViewModel
import com.example.playlist_maker3.media.ui.PlaylistsViewModel
import com.example.playlist_maker3.player.ui.PlayerViewModel
import com.example.playlist_maker3.search.ui.SearchViewModel
import com.example.playlist_maker3.settings.ui.SettingsActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PlayerViewModel(get(), get())
    }

    viewModel {
        SearchViewModel(get(), get())
    }

    viewModel {
        SettingsActivityViewModel(get(), get())
    }
    viewModel {
        PlaylistsViewModel(get())
    }
    viewModel {
        FavoritesViewModel(get())
    }
    viewModel {
        MediaViewModel()
    }

    viewModel { NewPlaylistViewModel(get()) }

    viewModel { PlaylistsBottomSheetViewModel(get()) }

    viewModel { PlaylistMenuBottomSheetViewModel(get()) }

    viewModel { PlaylistViewModel(get()) }

}