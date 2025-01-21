package com.example.playlist_maker3.settings.domain.interactor

import com.example.playlist_maker3.settings.ui.DarkThemeSettings

interface DarkThemeInteractor {
    fun getThemeSettings(): DarkThemeSettings
    fun updateThemeSetting(settings: DarkThemeSettings)
}