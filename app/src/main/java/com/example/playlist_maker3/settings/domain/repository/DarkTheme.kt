package com.example.playlist_maker3.settings.domain.repository

interface DarkTheme {
    fun getThemeSettings(): Boolean
    fun updateThemeSettings(state: Boolean)

}