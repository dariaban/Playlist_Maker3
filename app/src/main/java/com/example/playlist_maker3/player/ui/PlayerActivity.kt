package com.example.playlist_maker3.player.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.example.playlist_maker3.databinding.ActivityPlayerBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlist_maker3.R
import com.example.playlist_maker3.search.domain.model.Track
import com.example.playlist_maker3.player.util.TimeFormatter

class PlayerActivity : AppCompatActivity() {
    companion object {
        const val TRACK = "TRACK"
        fun startActivity(context: Context, track: Track) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(TRACK, track)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityPlayerBinding
    private var mainThreadHandler: Handler? = null
    private lateinit var viewModel: PlayerViewModel


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val track: Track = intent.getParcelableExtra(TRACK, Track::class.java)!!

        viewModel = ViewModelProvider(
            this,
            PlayerViewModel.getViewModelFactory()
        )[PlayerViewModel::class.java]
        mainThreadHandler = Handler(Looper.getMainLooper())
        binding.playButton.setOnClickListener { viewModel.playbackControl() }

        viewModel.observeState().observe(this) {
            render(it)
        }
        viewModel.observeProgressTimeState().observe(this) {
            progressTimeViewUpdate(it)
        }

        binding.backButton.setOnClickListener { finish() }
        binding.trackName.text = track.trackName
        binding.artistName.text = track.artistName
        binding.albumNameData.text = track.collectionName
        binding.trackYearData.text = track.releaseDate.substring(0, 4)
        binding.trackGenreData.text = track.primaryGenreName
        binding.trackCountryData.text = track.country
        binding.trackLengthTime.text = TimeFormatter.format(track.trackTimeMillis)

        Glide.with(applicationContext)
            .load(track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(10))
            .into(binding.image)

        viewModel.prepare(track)
    }

    private fun render(state: PlayerState) {
        when (state) {
            PlayerState.STATE_PLAYING -> start()
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> pause()
            else -> {}
        }
    }

    private fun start() {
        binding.playButton.setImageResource(R.drawable.pause)
    }

    private fun pause() {
        binding.playButton.setImageResource(R.drawable.play_button)
    }

    private fun progressTimeViewUpdate(progressTime: String) {
        binding.trackTime.text = progressTime
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }
}