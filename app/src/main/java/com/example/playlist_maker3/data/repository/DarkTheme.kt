package com.example.playlist_maker3.data.repository

interface DarkTheme {
    fun getThemePreferences(): Boolean
    fun saveTheme(state: Boolean)

}