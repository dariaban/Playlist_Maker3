package com.example.playlist_maker3.player.ui

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.ActivityPlayerBinding
import com.example.playlist_maker3.player.domain.PlayerState
import com.example.playlist_maker3.player.util.TimeFormatter
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.Job
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerActivity : AppCompatActivity() {
    companion object {

        const val TRACK = "track"
    }

    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModel()

    private lateinit var track: Track


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener { viewModel.playbackControl() }

        viewModel.observeState().observe(this) {
            render(it)
        }
        viewModel.observeFavoriteState().observe(this) {
            favoriteRender(it)}

        viewModel.observeProgressTimeState().observe(this) {
            progressTimeViewUpdate(it)
        }
        intent.getSerializableExtra(TRACK, Track::class.java)?.let { track = it }


        bind(track)

        binding.backButton.setOnClickListener { finish() }

        binding.likeButton.setOnClickListener { viewModel.favoriteClicked(track) }

        viewModel.prepare(track)
    }

    private fun bind(track: Track) {
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
    }

    private fun favoriteRender(favoriteChecked: Boolean) {
        if (favoriteChecked)
            binding.likeButton.setImageResource(R.drawable.liked_button)
        else binding.likeButton.setImageResource(R.drawable.like_button)
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