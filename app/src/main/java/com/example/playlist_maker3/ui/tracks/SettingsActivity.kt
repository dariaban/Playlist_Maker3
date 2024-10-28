package com.example.playlist_maker3.ui.tracks


import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.playlist_maker3.Creator
import com.example.playlist_maker3.R
import com.example.playlist_maker3.domain.use_case.DarkThemeInteractor
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlin.properties.Delegates

private lateinit var themeSwitcher: SwitchMaterial
private lateinit var darkThemeInteractor: DarkThemeInteractor

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        themeSwitcher = findViewById(R.id.button_switch)
        val backButton = findViewById<MaterialButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        val shareButton = findViewById<MaterialButton>(R.id.share_button)
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/x-uri")
            Intent.createChooser(shareIntent, null)
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.android_url))
            startActivity(shareIntent)
        }
        val supportButton = findViewById<MaterialButton>(R.id.button_support)
        supportButton.setOnClickListener {
            val supportIntent = Intent(Intent.ACTION_SEND)
            supportIntent.setType("text/plain")
            supportIntent.setPackage("com.google.android.gm")
            supportIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.support_text))
            supportIntent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.support_email))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.support_subject))
            startActivity(supportIntent)
        }
        val agreementButton = findViewById<MaterialButton>(R.id.button_agreement)
        agreementButton.setOnClickListener {
            val agreementIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.agreement_url)))
            startActivity(agreementIntent)
        }
        val sharedPreferencesTheme = getSharedPreferences(THEME_PREFERENCES, MODE_PRIVATE)
        darkThemeInteractor = DarkThemeInteractor(sharedPreferencesTheme)
        val sharedPreferencesThemeSaved = darkThemeInteractor.getThemePreferences()
        if (!sharedPreferencesThemeSaved) {
            themeSwitcher.isChecked
        }
        checkSwitcher(sharedPreferencesThemeSaved)

        themeSwitcher.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                darkThemeInteractor.switchTheme(true)
                themeSwitcher.isChecked
            } else {
                darkThemeInteractor.switchTheme(false)
            }

        }


    }

    private fun checkSwitcher(sharedPreferencesThemeSaved: Boolean) {
        if (sharedPreferencesThemeSaved) {
            themeSwitcher.isChecked = true
        }
    }
    companion object{
        const val THEME_PREFERENCES = "theme_preferences"
    }
}




