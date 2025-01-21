package com.example.playlist_maker3.settings.domain.impl

import com.example.playlist_maker3.settings.ui.DarkThemeSettings
import com.example.playlist_maker3.settings.domain.repository.DarkTheme
import com.example.playlist_maker3.settings.domain.interactor.DarkThemeInteractor


class DarkThemeInteractorImpl(private val darkTheme: DarkTheme) : DarkThemeInteractor {

    override fun getThemeSettings(): DarkThemeSettings {
        return darkTheme.getThemeSettings()
    }

    override fun updateThemeSetting(settings: DarkThemeSettings) {
        return darkTheme.updateThemeSettings(settings)
    }
}

