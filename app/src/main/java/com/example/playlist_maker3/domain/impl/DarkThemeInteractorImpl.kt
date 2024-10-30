package com.example.playlist_maker3.domain.impl
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.example.playlist_maker3.data.repository.DarkTheme
import com.example.playlist_maker3.domain.api.DarkThemeInteractor


class DarkThemeInteractorImpl(private val darkTheme: DarkTheme) : DarkThemeInteractor {

    override fun checkState(): Boolean{
        return darkTheme.getThemePreferences()
    }
    override fun saveTheme(state: Boolean) {
      darkTheme.saveTheme(state)
    }





}

