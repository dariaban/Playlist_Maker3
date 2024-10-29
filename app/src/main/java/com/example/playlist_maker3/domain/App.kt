package com.example.playlist_maker3.domain
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlist_maker3.Creator
import kotlin.properties.Delegates

class App: Application() {
    private var darkTheme by Delegates.notNull<Boolean>()
    override fun onCreate() {
        super.onCreate()
        val darkThemeInteractor = Creator.provideDarkThemeInteractor(this)
        darkTheme = darkThemeInteractor.checkState()
        switchTheme(darkTheme)
    }

    private fun switchTheme(darkTheme: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme){
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}



