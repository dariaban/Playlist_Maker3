package com.example.playlist_maker3.settings.domain.impl

import com.example.playlist_maker3.settings.domain.repository.DarkTheme
import com.example.playlist_maker3.settings.domain.interactor.DarkThemeInteractor


class DarkThemeInteractorImpl(private val darkTheme: DarkTheme) : DarkThemeInteractor {

    override fun getThemeSettings(): Boolean {
        return darkTheme.getThemeSettings()
    }

    override fun updateThemeSetting(state: Boolean) {
        return darkTheme.updateThemeSettings(state)
    }
}

