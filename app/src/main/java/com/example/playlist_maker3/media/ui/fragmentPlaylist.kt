package com.example.playlist_maker3.media.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.FragmentPlaylistBinding
import com.example.playlist_maker3.media.domain.PlaylistsScreenState
import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.media.ui.adapter.PlaylistsAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistBinding
    private val viewModel by viewModel<PlaylistsViewModel>()
    private val playlistsAdapter = PlaylistsAdapter {
        clickOnPlaylist()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.contentFlow.collect { screenState ->
                render(screenState)
            }
        }

        binding.newPlaylistButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_libraryFragment_to_newPlaylistFragment
            )
        }
    }

    private fun render(state: PlaylistsScreenState) {
        when (state) {
            is PlaylistsScreenState.Content -> showContent(state.playlists)
            PlaylistsScreenState.Empty -> showPlaceholder()
        }
    }

    private fun showPlaceholder() {
        binding.apply {
            notFoundText.visibility = View.VISIBLE
            notFoundImage.visibility = View.VISIBLE
            recyclerViewPlaylist.visibility = View.GONE
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showContent(content: List<Playlist>) {

        binding.apply {
            notFoundText.visibility = View.GONE
            notFoundImage.visibility = View.GONE
            recyclerViewPlaylist.visibility = View.VISIBLE
        }

        playlistsAdapter.apply {
            playlists.clear()
            playlists.addAll(content)
            notifyDataSetChanged()
        }
    }

    private fun initAdapter() {
        binding.recyclerViewPlaylist.adapter = playlistsAdapter
        binding.recyclerViewPlaylist.addItemDecoration(PlaylistsOffsetItemDecoration(requireContext()))
    }

    private fun clickOnPlaylist() {
        if (!viewModel.isClickable) return
        viewModel.onPlaylistClick()
        Toast
            .makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        fun newInstance() = PlaylistsFragment()
    }
}



