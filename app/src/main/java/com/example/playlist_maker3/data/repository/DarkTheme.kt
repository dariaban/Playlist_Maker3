package com.example.playlist_maker3.data.repository

interface DarkTheme {
    fun getThemePreferences(): Boolean
    fun switchTheme( darkTheme: Boolean)
    fun saveTheme(state: Boolean)
}