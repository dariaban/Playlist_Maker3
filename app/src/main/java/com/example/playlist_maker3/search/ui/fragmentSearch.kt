package com.example.playlist_maker3.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.FragmentSearchBinding
import com.example.playlist_maker3.player.ui.AudioPlayerFragment
import com.example.playlist_maker3.search.domain.SearchState
import com.example.playlist_maker3.search.domain.model.Track
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private var input: String? = null
    private val viewModel: SearchViewModel by viewModel()
    private val tracks = mutableListOf<Track>()
    private val adapter = TrackAdapter(tracks) {
        viewModel.addTrack(it)
        if (clickDebounce()) {
            viewModel.addTrack(it)
            findNavController().navigate(R.id.action_searchFragment_to_audioPlayer,
                AudioPlayerFragment.createArgs(it))
        }
    }
    private val historyAdapter = TrackAdapter(emptyList()) {
        if (clickDebounce()) {
            viewModel.addTrack(it)
            findNavController().navigate(R.id.action_searchFragment_to_audioPlayer,
                AudioPlayerFragment.createArgs(it))
        }
    }

    private var isClickAllowed = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerViews()
        setupListeners()
        if(binding.searchBar.text.isNotEmpty()){
            showTrackList()
        } else {
            viewModel.loadSearchHistory()
        }
        viewModel.observeState().observe(viewLifecycleOwner) { render(it) }

    }

    private fun setupToolbar() {
        binding.searchToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerViews() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.searchHistoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.searchHistoryRecyclerView.adapter = historyAdapter
    }

    private fun setupListeners() {


        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(s?.toString()?: "")
                binding.clearIcon.isVisible = !s.isNullOrEmpty()
                binding.searchHistory.isVisible = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        binding.refreshButton.setOnClickListener {
            viewModel.searchDebounce(input?:"")
        }

        binding.clearIcon.setOnClickListener {
            binding.searchBar.text.clear()
            binding.searchHistory.isVisible = true
        }

        binding.cleanHistory.setOnClickListener {
            viewModel.clearHistory()
            binding.searchHistory.isVisible = false
        }

    }


    private fun render(state: SearchState) {
        when (state) {
            is SearchState.SearchList -> {
                adapter.updateTracks(state.tracks)
                showTrackList()
            }
            is SearchState.Empty -> showNotFoundPage()
            is SearchState.NetworkError -> showInternetErrorPage()
            is SearchState.Loading -> showLoadingPage()
            is SearchState.HistoryList -> showSearchHistory(state.historyTracks)
            SearchState.NothingFound -> showNotFoundPage()
        }
    }

    private fun showSearchHistory(history: List<Track>) {
        if (history.isNotEmpty() && binding.searchBar.text.isNullOrEmpty()) {
            binding.searchHistory.isVisible = true
            historyAdapter.updateTracks(history)
            binding.recyclerView.isVisible = false
            binding.progressCircular.isVisible = false
            binding.searchError.isVisible = false
        } else {
            binding.searchHistory.isVisible = false
        }
    }

    private fun showLoadingPage() {
        binding.searchHistory.isVisible = false
        binding.recyclerView.isVisible = false
        binding.progressCircular.isVisible = true
        binding.searchError.isVisible = false
        binding.networkError.isVisible = false
    }

    private fun showTrackList() {
        binding.searchHistory.isVisible = false
        binding.recyclerView.isVisible = true
        binding.progressCircular.isVisible = false
        binding.searchError.isVisible = false
        binding.networkError.isVisible = false
    }

    private fun showNotFoundPage() {
        binding.searchHistory.isVisible = false
        binding.recyclerView.isVisible = false
        binding.progressCircular.isVisible = false
        binding.searchError.isVisible = true
        binding.networkError.isVisible = false
    }

    private fun showInternetErrorPage() {
        binding.searchHistory.isVisible = false
        binding.recyclerView.isVisible = false
        binding.progressCircular.isVisible = false
        binding.searchError.isVisible = false
        binding.networkError.isVisible = true
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewLifecycleOwner.lifecycleScope.launch{
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    override fun onResume() {
    super.onResume()
        isClickAllowed=true
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(INPUT_TEXT, binding.searchBar.toString())
    }

    companion object {
        private const val INPUT_TEXT = "INPUT_TEXT"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}