package com.example.playlist_maker3.settings.domain.interactor

interface DarkThemeInteractor {
    fun getThemeSettings(): Boolean
    fun updateThemeSetting(state: Boolean)
}