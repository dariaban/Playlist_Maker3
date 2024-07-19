package com.example.playlist_maker3

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class App: Application() {
    private var darkTheme : Boolean = false
    override fun onCreate() {
        super.onCreate()
        val themePreference = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE)
        darkTheme = themePreference.getBoolean(PREFERENCES_KEY,false)
        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled){
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
    }



