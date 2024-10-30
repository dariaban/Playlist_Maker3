package com.example.playlist_maker3.ui.tracks

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlist_maker3.Creator
import com.example.playlist_maker3.R
import com.example.playlist_maker3.presentation.PlayerPresenter
import com.example.playlist_maker3.domain.api.PlaybackControl
import com.example.playlist_maker3.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale


class PlayerActivity : AppCompatActivity(), PlayerPresenter {
    private lateinit var track: Track
    private lateinit var playButton: ImageButton
    private lateinit var previewUri: String
    private lateinit var trackTime: TextView
    private lateinit var playbackControl: PlaybackControl
    private var mainThreadHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntentCompat.getSerializableExtra(intent, SELECTED_TRACK_KEY, Track::class.java)?.let {
            track = it
        }
        setContentView(R.layout.activity_player)
        playbackControl = Creator.createPlayControl(this)
        trackTime = findViewById(R.id.trackTime)
        playButton = findViewById(R.id.play_button)
        val trackName = findViewById<TextView>(R.id.trackName)
        val artistName = findViewById<TextView>(R.id.artistName)
        val trackLength = findViewById<TextView>(R.id.trackLengthTime)
        val albumName = findViewById<TextView>(R.id.albumName)
        val trackYear = findViewById<TextView>(R.id.trackYearData)
        val genre = findViewById<TextView>(R.id.trackGenreData)
        val country = findViewById<TextView>(R.id.trackCountryData)
        val artwork = findViewById<ImageView>(R.id.image)


        mainThreadHandler = Handler(Looper.getMainLooper())
        playButton.setOnClickListener { playbackControl.playbackControl() }
        val imageBack = findViewById<Toolbar>(R.id.backButton)
        imageBack.setOnClickListener { finish() }
        trackName.text = track.trackName
        artistName.text = track.artistName
        albumName.text = track.collectionName
        trackYear.text = track.releaseDate.substring(0, 4)
        genre.text = track.primaryGenreName
        country.text = track.country
        trackLength.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        playbackControl.prepare(track)
        Glide.with(applicationContext)
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(10))
            .into(artwork)
    }
    override fun startPlayer() {
        playButton.setImageResource(R.drawable.pause)
        mainThreadHandler?.post(
            playbackControl.createUpdateProgressTimeRunnable()
        )
    }
    override fun pausePlayer() {
        playButton.setImageResource(R.drawable.play_button)
    }
    override fun progressTimeViewUpdate(progressTime: String) {
        trackTime.text = progressTime
    }
    override fun playButtonEnabled() {
        playButton.isEnabled = true
    }
    override fun postDelayed(runnable: Runnable) {
        mainThreadHandler?.postDelayed(runnable, CURRENT_POSITION_DELAY)
    }
    override fun removeCallbacks(runnable: Runnable) {
        mainThreadHandler?.removeCallbacks(runnable)
    }
    override fun onPause() {
        super.onPause()
        playbackControl.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        playbackControl.release()
    }
    companion object{
        const val SELECTED_TRACK_KEY = "key_for_last_track"
        const val CURRENT_POSITION_DELAY = 3L
    }
}
