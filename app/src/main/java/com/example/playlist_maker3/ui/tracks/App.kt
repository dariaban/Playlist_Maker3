package com.example.playlist_maker3.ui.tracks
import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlist_maker3.domain.use_case.DarkThemeInteractor

class App: Application() {
    private var darkTheme : Boolean = false
    override fun onCreate() {
        super.onCreate()
        val themePreference = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE)
        val darkThemeInteractor = DarkThemeInteractor(themePreference)
        darkTheme = darkThemeInteractor.getThemePreferences()
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
    companion object{
        const val THEME_PREFERENCES = "theme_preferences"
    }
}



