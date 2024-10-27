package com.example.playlist_maker3.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.playlist_maker3.domain.api.DarkTheme


class DarkThemeImpl(themePreferences: SharedPreferences) : DarkTheme {
    override fun getThemePreferences(themePreferences: SharedPreferences): Boolean {
        val darkTheme = themePreferences.getBoolean(PREFERENCES_KEY, false)
        return darkTheme
    }

    override fun switchTheme(themePreferences: SharedPreferences, darkTheme: Boolean) {
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            saveTheme(themePreferences, true)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            saveTheme(themePreferences, true)
        }
    }

    override fun saveTheme(themePreferences: SharedPreferences, state: Boolean) {
        themePreferences.edit()
            .putBoolean(PREFERENCES_KEY, state)
            .apply()
    }

    companion object {
        const val PREFERENCES_KEY = "KEY_FOR_THEME_PREFERENCE"
    }

}