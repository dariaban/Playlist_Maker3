package com.example.playlist_maker3.main.ui
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlist_maker3.R
import com.example.playlist_maker3.media.ui.MediaActivity
import com.example.playlist_maker3.search.ui.SearchActivity
import com.example.playlist_maker3.settings.ui.SettingsActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonSearch = findViewById<MaterialButton>(R.id.button_search)
        buttonSearch.setOnClickListener {
            val displayIntentSearch = Intent(this, SearchActivity::class.java)
            startActivity(displayIntentSearch)
        }
        val buttonMedia = findViewById<MaterialButton>(R.id.button_media)
        buttonMedia.setOnClickListener {
            val displayIntentMedia = Intent(this, MediaActivity::class.java)
            startActivity(displayIntentMedia)
        }
        val buttonSettings = findViewById<MaterialButton>(R.id.button_settings)
        buttonSettings.setOnClickListener {
            val displayIntentSettings = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntentSettings)
        }
    }
}
