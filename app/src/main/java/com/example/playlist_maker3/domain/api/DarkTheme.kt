package com.example.playlist_maker3.domain.api

import android.content.SharedPreferences

interface DarkTheme {
    fun getThemePreferences(themePreferences: SharedPreferences): Boolean
    fun switchTheme(themePreferences: SharedPreferences, darkTheme: Boolean)
    fun saveTheme(themePreferences: SharedPreferences, state: Boolean)
}