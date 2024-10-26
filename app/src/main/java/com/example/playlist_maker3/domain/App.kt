package com.example.playlist_maker3.domain

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlist_maker3.ui.tracks.PREFERENCES_KEY
import com.example.playlist_maker3.ui.tracks.THEME_PREFERENCES

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



