package com.example.playlist_maker3.player.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.FragmentAudioplayerBinding
import com.example.playlist_maker3.media.ui.PlaylistsBottomSheet
import com.example.playlist_maker3.player.domain.PlayerState
import com.example.playlist_maker3.player.util.TimeFormatter
import com.example.playlist_maker3.search.domain.model.Track
import org.koin.androidx.viewmodel.ext.android.viewModel

class AudioPlayerFragment : Fragment() {

    private var _binding: FragmentAudioplayerBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlayerViewModel by viewModel()
    private lateinit var track: Track


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioplayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        track = arguments?.getSerializable(Constants.TRACK) as Track

        super.onViewCreated(view, savedInstanceState)
        binding.playButton.setOnClickListener { viewModel.playbackControl() }

        binding.addButton.setOnClickListener { button ->
            (button as? ImageView)?.let { startAnimation(it) }
            PlaylistsBottomSheet.newInstance(track).show(childFragmentManager, PlaylistsBottomSheet.TAG)
        }


        viewModel.observeFavoriteState().observe(viewLifecycleOwner) {
            favoriteRender(it)
        }
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
        viewModel.observeProgressTimeState().observe(viewLifecycleOwner) {
            progressTimeViewUpdate(it)
        }

        bind(track)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.likeButton.setOnClickListener { viewModel.favoriteClicked(track) }



        viewModel.prepare(track)

    }

    private fun bind(track: Track) {
        binding.trackName.text = track.trackName
        binding.artistName.text = track.artistName
        binding.albumNameData.text = track.collectionName
        binding.trackYearData.text = track.releaseDate?.substring(0, 4)
        binding.trackGenreData.text = track.primaryGenreName
        binding.trackCountryData.text = track.country
        binding.trackLengthTime.text = TimeFormatter.format(track.trackTimeMillis)



        Glide.with(binding.image)
            .load(track.artworkUrl100?.replaceAfterLast('/', "512x512bb.jpg"))
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(10))
            .into(binding.image)
    }

    private fun favoriteRender(favoriteChecked: Boolean) {
        if (favoriteChecked) {
            binding.likeButton.setImageResource(R.drawable.liked_button)
        } else binding.likeButton.setImageResource(R.drawable.like_button)
    }

    private fun startAnimation(button: ImageView) {
        button.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.scale
            )
        )
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


    @Suppress("DEPRECATION")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        track = arguments?.getSerializable(Constants.TRACK) as Track
    }


    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

}