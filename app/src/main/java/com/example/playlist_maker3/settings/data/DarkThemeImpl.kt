package com.example.playlist_maker3.settings.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.playlist_maker3.App
import com.example.playlist_maker3.settings.ui.DarkThemeSettings
import com.example.playlist_maker3.settings.domain.repository.DarkTheme

class DarkThemeImpl(context: Context, val app: App) : DarkTheme {
    private val themePreferences: SharedPreferences = context.getSharedPreferences(
        THEME_PREFERENCES,
        MODE_PRIVATE
    )

    override fun getThemeSettings(): DarkThemeSettings {
        return DarkThemeSettings(themePreferences.getBoolean(PREFERENCES_KEY, false))
    }

    override fun updateThemeSettings(settings: DarkThemeSettings) {
        themePreferences.edit().putBoolean(PREFERENCES_KEY, settings.isDarkTheme).apply()
    }

    companion object {
        const val PREFERENCES_KEY = "KEY_FOR_THEME_PREFERENCE"
        const val THEME_PREFERENCES = "theme_preferences"
    }
}