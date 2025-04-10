package com.example.playlist_maker3.media.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.FragmentFavoritesBinding
import com.example.playlist_maker3.media.domain.FavoriteState
import com.example.playlist_maker3.player.ui.AudioPlayerFragment
import com.example.playlist_maker3.search.domain.model.Track
import com.example.playlist_maker3.search.ui.TrackAdapter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
        trackAdapter = TrackAdapter(tracks) { track -> onTrackClick(track)}
        binding.recyclerView.adapter = trackAdapter
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }
    }


    private fun render(state: FavoriteState) {
        when (state) {
            is FavoriteState.Content -> {
                showContent(state.tracks)
            }

            is FavoriteState.Empty -> showEmpty()
        }
    }


    private fun showContent(trackList: List<Track>) {
        binding.nothingFounded.visibility = View.GONE
        binding.empty.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        trackAdapter.updateTracks(trackList)
    }

    private fun onTrackClick(track: Track) {
        findNavController().navigate(
            R.id.action_libraryFragment_to_audioPlayer,
            AudioPlayerFragment.createArgs(track))
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
        fun createArgs(track: Track): Bundle = bundleOf(
            Constants.TRACK to Json.encodeToString(track))
    }
}