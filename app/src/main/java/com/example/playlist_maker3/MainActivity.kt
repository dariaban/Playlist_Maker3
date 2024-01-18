package com.example.playlist_maker3
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.google.android.material.button.MaterialButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button1 = findViewById<MaterialButton>(R.id.button1)
        button1.setOnClickListener {
            val displayIntent = Intent(this, SearchActivity::class.java)
            startActivity(displayIntent)
        }
        val button2 = findViewById<MaterialButton>(R.id.button2)
        button2.setOnClickListener {
            val displayIntent2 = Intent(this, MediaActivity::class.java)
            startActivity(displayIntent2)
        }
        val button3 = findViewById<MaterialButton>(R.id.button3)
        button3.setOnClickListener {
            val displayIntent3 = Intent(this, SettingsActivity::class.java)
            startActivity(displayIntent3)
        }
    }
}
