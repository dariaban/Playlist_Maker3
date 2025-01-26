package com.example.playlist_maker3

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlist_maker3.di.dataModule
import com.example.playlist_maker3.di.interactorModule
import com.example.playlist_maker3.di.repositoryModule
import com.example.playlist_maker3.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
        val sharedPreferences =
            getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE)
        val darkTheme = sharedPreferences.getBoolean(PREFERENCES_KEY, false)
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
        const val PREFERENCES_KEY = "KEY_FOR_THEME_PREFERENCE"
        const val THEME_PREFERENCES = "theme_preferences"
        fun applicationContext(): Context {
            return context!!.applicationContext
        }

    }
}



