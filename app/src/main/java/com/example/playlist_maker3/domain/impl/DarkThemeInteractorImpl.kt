package com.example.playlist_maker3.domain.impl
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.playlist_maker3.data.repository.DarkTheme
import com.example.playlist_maker3.domain.api.DarkThemeInteractor


class DarkThemeInteractorImpl(private val darkTheme: DarkTheme) : DarkThemeInteractor {
private val currentState = darkTheme.getThemePreferences()

    override fun checkState(): Boolean{
        return currentState
    }
    override fun switchTheme(state: Boolean) {
        if (state) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            darkTheme.saveTheme(true)
        } else {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            darkTheme.saveTheme(false)
        }
    }





}

