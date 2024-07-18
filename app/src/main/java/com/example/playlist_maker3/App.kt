package com.example.playlist_maker3

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {
    private var darkTheme = false
    override fun onCreate() {
        super.onCreate()
        val sharedPreferencesTheme = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE)
        darkTheme = sharedPreferencesTheme.getBoolean(PREFERENCES_KEY, false)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled){
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
    }



