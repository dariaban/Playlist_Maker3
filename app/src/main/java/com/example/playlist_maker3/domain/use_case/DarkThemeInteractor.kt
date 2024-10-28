package com.example.playlist_maker3.domain.use_case

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.playlist_maker3.data.repository.DarkTheme
import com.example.playlist_maker3.domain.use_case.HistoryInteractor.Companion.HISTORY_PREFERENCES


class DarkThemeInteractor(context: Context) : DarkTheme {
    private val themePreferences: SharedPreferences = context.getSharedPreferences(
        THEME_PREFERENCES,
        MODE_PRIVATE
    )
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
            saveTheme(false)
        }
    }

    override fun saveTheme( state: Boolean) {
        themePreferences.edit()
            .putBoolean(PREFERENCES_KEY, state)
            .apply()
    }

    companion object {
        const val PREFERENCES_KEY = "KEY_FOR_THEME_PREFERENCE"
        const val THEME_PREFERENCES = "theme_preferences"
    }

}

