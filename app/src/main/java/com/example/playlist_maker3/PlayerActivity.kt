package com.example.playlist_maker3

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale

const val SELECTED_TRACK_KEY = "key_for_last_track"
class PlayerActivity:AppCompatActivity() {
    private lateinit var track: Track
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_player)
       val backButton = findViewById<MaterialButton>(R.id.backButton)
        backButton.setOnClickListener {
           finish()
        }

IntentCompat.getSerializableExtra(intent,LAST_TRACK_KEY,Track::class.java)?.let {
    track=it
}
        val trackTimeFormat =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        val trackName: TextView = findViewById(R.id.trackName)
        trackName.text = track.trackName
        val artistName: TextView = findViewById(R.id.artistName)
        artistName.text = track.artistName
        val trackTime: TextView = findViewById(R.id.trackTime)
        trackTime.text = trackTimeFormat.toString()
        val trackArtwork: ImageView = findViewById(R.id.image)
        Glide.with(trackArtwork)
            .load(track.artworkUrl100.replaceAfterLast('/',"512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(10))
            .into(trackArtwork)
        val albumName : TextView = findViewById(R.id.albumNameData)
        albumName.text = track.collectionName
        val trackLength : TextView = findViewById(R.id.trackLengthTime)
        trackLength.text = trackTimeFormat.toString()
        val trackYear: TextView = findViewById(R.id.trackYearData)
        trackYear.text = track.releaseDate.dropLast(16)
        val trackGenre : TextView = findViewById(R.id.trackGenreData)
        trackGenre.text = track.primaryGenreName
        val trackCountry : TextView = findViewById(R.id.trackCountryData)
        trackCountry.text = track.country
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SELECTED_TRACK_KEY, track)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getBundle(SELECTED_TRACK_KEY)
    }

}
