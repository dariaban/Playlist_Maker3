package com.example.playlist_maker3.domain

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlist_maker3.Creator
import kotlin.properties.Delegates

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        val darkThemeInteractor = Creator.provideDarkThemeInteractor()
        val darkTheme = darkThemeInteractor.checkState()
        switchTheme(darkTheme)
    }

    private fun switchTheme(darkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    companion object {
        private var context: App? = null

        fun applicationContext(): Context {
            return context!!.applicationContext
        }

    }
}



