package com.example.playlist_maker3.settings.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.playlist_maker3.settings.domain.repository.DarkTheme

class DarkThemeImpl(context: Context) : DarkTheme {
    private val themePreferences: SharedPreferences = context.getSharedPreferences(
        THEME_PREFERENCES,
        MODE_PRIVATE
    )

    override fun getThemeSettings(): Boolean {
        return themePreferences.getBoolean(PREFERENCES_KEY, false)
    }

    override fun updateThemeSettings(state: Boolean) {
        themePreferences.edit().putBoolean(PREFERENCES_KEY, state).apply()
    }

    companion object {
        const val PREFERENCES_KEY = "KEY_FOR_THEME_PREFERENCE"
        const val THEME_PREFERENCES = "theme_preferences"
    }
}