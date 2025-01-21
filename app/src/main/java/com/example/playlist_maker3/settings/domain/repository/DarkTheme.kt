package com.example.playlist_maker3.settings.domain.repository

import com.example.playlist_maker3.settings.ui.DarkThemeSettings

interface DarkTheme {
    fun getThemeSettings(): DarkThemeSettings
    fun updateThemeSettings(settings: DarkThemeSettings)

}