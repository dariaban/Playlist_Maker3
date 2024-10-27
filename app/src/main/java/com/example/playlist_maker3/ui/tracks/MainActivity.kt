package com.example.playlist_maker3.ui.tracks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlist_maker3.Creator
import com.example.playlist_maker3.R
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button_search = findViewById<MaterialButton>(R.id.button_search)
        button_search.setOnClickListener {
            val displayIntent_search = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent_search)
        }
        val button_media = findViewById<MaterialButton>(R.id.button_media)
        button_media.setOnClickListener {
            val displayIntent_media = Intent(this, MediaActivity::class.java)
            startActivity(displayIntent_media)
        }
        val button_settings = findViewById<MaterialButton>(R.id.button_settings)
        button_settings.setOnClickListener {
            val displayIntent_settings = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent_settings)
        }
    }
}
