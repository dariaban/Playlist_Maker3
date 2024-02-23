package com.example.playlist_maker3

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val backButton = findViewById<MaterialButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        val shareButton = findViewById<MaterialButton>(R.id.share_button)
        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/x-uri")
            Intent.createChooser(shareIntent,null)
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.android_url))
            startActivity(shareIntent)
        }
        val supportButton = findViewById<MaterialButton>(R.id.button_support)
        supportButton.setOnClickListener {
            val supportIntent = Intent(Intent.ACTION_SEND)
            supportIntent.setType("text/plain")
            supportIntent.setPackage("com.google.android.gm")
            supportIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.support_text))
            supportIntent.putExtra(Intent.EXTRA_EMAIL,getString(R.string.support_email))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.support_subject))
            startActivity(supportIntent)
        }
        val agreementButton = findViewById<MaterialButton>(R.id.button_agreement)
        agreementButton.setOnClickListener {
            val agreementIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.agreement_url)))
            startActivity(agreementIntent)
        }
        val switchTheme = findViewById<SwitchMaterial>(R.id.button_switch)
        switchTheme.setOnCheckedChangeListener { _, b ->
            if (b) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    }
}
