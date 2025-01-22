package com.example.playlist_maker3.settings.domain

sealed class DarkThemeState {
    data object Dark: DarkThemeState()
    data object Bright: DarkThemeState()
}