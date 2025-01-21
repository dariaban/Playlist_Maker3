package com.example.playlist_maker3.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlist_maker3.creator.Creator
import com.example.playlist_maker3.settings.domain.interactor.DarkThemeInteractor
import com.example.playlist_maker3.sharing.domain.ShareInteractor

class SettingsActivityViewModel(
    private val shareInteractor: ShareInteractor,
    private val darkThemeInteractor: DarkThemeInteractor
) : ViewModel() {


    companion object {
        fun getViewModelFactory(): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val shareInteractor =
                    Creator.provideShareInteractor()
                val darkThemeInteractor =
                    Creator.provideDarkThemeInteractor()
                SettingsActivityViewModel(
                    shareInteractor,
                    darkThemeInteractor,
                )
            }
        }
    }


    fun updateThemeSetting(settings: Boolean) {
        darkThemeInteractor.updateThemeSetting(DarkThemeSettings(settings))
    }


    fun getTheme(): Boolean {
        val theme = darkThemeInteractor.getThemeSettings()
        return theme.isDarkTheme
    }

    fun open() {
        shareInteractor.open()
    }

    fun share() {
        shareInteractor.share()
    }

    fun support() {
        shareInteractor.supportEmail()
    }
}