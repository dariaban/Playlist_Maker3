package com.example.playlist_maker3.media.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlist_maker3.databinding.FragmentFavoritesBinding
import com.example.playlist_maker3.media.domain.FavoriteState
import com.example.playlist_maker3.player.ui.PlayerActivity
import com.example.playlist_maker3.search.domain.model.Track
import com.example.playlist_maker3.search.ui.TrackAdapter
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModel()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var trackAdapter: TrackAdapter
    private val tracks = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        trackAdapter = TrackAdapter(emptyList()) { track -> onTrackClick(track) }
        binding.recyclerView.adapter = trackAdapter
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.fillData()
    }

    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.Content -> showContent(state.tracks)
            is FavoriteState.Empty -> showEmpty()
            FavoriteState.Loading -> showLoading(tracks)
        }
    }

    private fun showLoading(tracks: List<Track>) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.nothingFounded.visibility = View.GONE
        binding.empty.visibility = View.GONE
        trackAdapter.updateTracks(tracks)
    }

    private fun showContent(trackList: List<Track>) {
        binding.nothingFounded.visibility = View.GONE
        binding.empty.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        tracks.clear()
        tracks.addAll(trackList)
        trackAdapter.notifyDataSetChanged()
    }

    private fun onTrackClick(track: Track) {
        activity?.let { context ->
            Intent(context, PlayerActivity::class.java).apply {
                putExtra(PlayerActivity.TRACK, track)
                context.startActivity(this)
            }
        }
    }

    private fun showEmpty() {
        binding.recyclerView.visibility = View.GONE
        binding.nothingFounded.visibility = View.VISIBLE
        binding.empty.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FavoritesFragment()
    }
}