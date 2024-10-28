package com.example.playlist_maker3.domain.use_case

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.playlist_maker3.data.repository.DarkTheme


class DarkThemeInteractor(private val themePreferences: SharedPreferences) : DarkTheme {
    override fun getThemePreferences(): Boolean {
        val darkTheme = themePreferences.getBoolean(PREFERENCES_KEY, false)
        return darkTheme
    }

    override fun switchTheme(darkTheme: Boolean) {
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            saveTheme(true)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            saveTheme(true)
        }
    }

    override fun saveTheme( state: Boolean) {
        themePreferences.edit()
            .putBoolean(PREFERENCES_KEY, state)
            .apply()
    }

    companion object {
        const val PREFERENCES_KEY = "KEY_FOR_THEME_PREFERENCE"
    }

}

