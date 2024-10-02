package com.example.playlist_maker3

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.IntentCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Locale

const val SELECTED_TRACK_KEY = "key_for_last_track"
const val CURRENT_POSITION_DELAY = 3L

class PlayerActivity : AppCompatActivity() {
    private lateinit var track: Track
    private val mediaPlayer = MediaPlayer()
    private lateinit var playButton: ImageButton
    private lateinit var previewUri: String
    private lateinit var trackTime: TextView


    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }

    private var playerState = STATE_DEFAULT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        IntentCompat.getSerializableExtra(intent, LAST_TRACK_KEY, Track::class.java)?.let {
            track = it
        }
        setContentView(R.layout.activity_player)
        previewUri = track.previewUrl
        playButton = findViewById(R.id.play_button)
        val backButton = findViewById<Toolbar>(R.id.backButton)
        backButton.setNavigationOnClickListener {
            finish()
        }
        val trackTimeFormat =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        val trackName: TextView = findViewById(R.id.trackName)
        trackName.text = track.trackName
        val artistName: TextView = findViewById(R.id.artistName)
        artistName.text = track.artistName
        trackTime = findViewById(R.id.trackTime)
        val trackArtwork: ImageView = findViewById(R.id.image)
        Glide.with(trackArtwork)
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .transform(RoundedCorners(10))
            .into(trackArtwork)
        val albumName: TextView = findViewById(R.id.albumNameData)
        albumName.text = track.collectionName
        val trackLength: TextView = findViewById(R.id.trackLengthTime)
        trackLength.text = trackTimeFormat.toString()
        val trackYear: TextView = findViewById(R.id.trackYearData)
        trackYear.text = track.releaseDate.dropLast(16)
        val trackGenre: TextView = findViewById(R.id.trackGenreData)
        trackGenre.text = track.primaryGenreName
        val trackCountry: TextView = findViewById(R.id.trackCountryData)
        trackCountry.text = track.country
        trackTime.text = getString(R.string.trackStarts)
        preparePlayer()
        playButton.setOnClickListener {
            playbackControl()
        }
    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(previewUri)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playButton.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        timer()
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
        handler.removeCallbacks(currentStateRunnable)
    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
                playButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.play_button
                    )
                )
            }

            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
                playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.pause))
                mediaPlayer.setOnCompletionListener {
                    handler.removeCallbacks(currentStateRunnable)
                    playerState = STATE_PREPARED
                    playButton.setImageDrawable(
                        AppCompatResources.getDrawable(
                            this,
                            R.drawable.play_button
                        )
                    )
                    trackTime.text = getString(R.string.trackStarts)
                }
            }
        }
    }

    private val handler = Handler(Looper.getMainLooper())
    private val currentStateRunnable = Runnable { timer() }
    private fun timer() {
        trackTime.text = SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(mediaPlayer.currentPosition)
        handler.postDelayed(currentStateRunnable, CURRENT_POSITION_DELAY)
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(currentStateRunnable)
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
