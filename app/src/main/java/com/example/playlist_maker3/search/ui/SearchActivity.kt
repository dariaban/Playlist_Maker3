package com.example.playlist_maker3.search.ui;

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlist_maker3.databinding.ActivitySearchBinding
import com.example.playlist_maker3.search.domain.model.Track
import com.example.playlist_maker3.player.ui.PlayerActivity
import com.google.android.material.button.MaterialButton

class SearchActivity : AppCompatActivity() {
    companion object {
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }

    private val adapter = TrackAdapter(emptyList()) { track: Track ->
        if (clickDebounce()) {
            viewModel.addTrack(track)
            PlayerActivity.startActivity(this, track)
        }
    }
    private val historyAdapter = TrackAdapter(emptyList()) {
        if (clickDebounce()) {
            viewModel.addTrack(it)
            PlayerActivity.startActivity(this, it)
        }
    }


    private lateinit var inputEditText: EditText
    private lateinit var tracksList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var historyList: RecyclerView
    private lateinit var searchHistory: LinearLayout
    private lateinit var notFoundPage: LinearLayout
    private lateinit var internetErrorPage: LinearLayout
    private lateinit var backButton: MaterialButton
    private lateinit var clearButton: ImageView
    private lateinit var clearHistoryButton: Button
    private lateinit var refreshButton: MaterialButton
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(
            this, SearchViewModel.getViewModelFactory()
        )[SearchViewModel::class.java]
        inputEditText = binding.searchBar
        tracksList = binding.recyclerView
        progressBar = binding.progressCircular
        historyList = binding.recyclerViewHistory
        searchHistory = binding.historyView
        notFoundPage = binding.searchError
        internetErrorPage = binding.networkError
        backButton = binding.backButton
        clearButton = binding.clearIcon
        refreshButton = binding.refreshButton
        clearHistoryButton = binding.cleanHistory
        tracksList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        tracksList.adapter = adapter
        historyList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        historyList.adapter = historyAdapter
        inputEditText.setOnFocusChangeListener() { _, hasFocus ->
            if (hasFocus) binding.historyView.visibility = View.GONE
        }
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchDebounce(changedText = s?.toString() ?: "")
                clearButton.isVisible = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                hideKeyboard(inputEditText)
            }
        })

        backButton.setOnClickListener {
            finish()
        }

        clearButton.setOnClickListener {
            inputEditText.text.clear()
        }

        clearHistoryButton.setOnClickListener {
            viewModel.clearHistory()
            binding.historyView.isVisible = false
        }
        refreshButton.setOnClickListener {
            showTrackList()
        }

        viewModel.observeState().observe(this) {
            render(it)
        }
        viewModel.loadSearchHistory()
        viewModel.observeHistory().observe(this) {
            showSearchHistory(it)
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
            is SearchState.NothingFound -> showNotFoundPage()
        }
    }

    private fun showSearchHistory(history: List<Track>) {
        if (history.isNotEmpty()) {
            historyList.isVisible = true
            binding.historyView.isVisible = true
            historyAdapter.updateTracks(history)
            historyAdapter.notifyDataSetChanged()
            tracksList.isVisible = false
            progressBar.isVisible = false
            notFoundPage.isVisible = false
        } else {
            binding.historyView.isVisible = false

        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY_MILLIS)
        }
        return current
    }

    private fun showLoadingPage() {
        searchHistory.isVisible = false
        tracksList.isVisible = false
        progressBar.isVisible = true
        notFoundPage.isVisible = false
        internetErrorPage.isVisible = false
    }

    private fun showTrackList() {
        searchHistory.isVisible = false
        tracksList.isVisible = true
        progressBar.isVisible = false
        notFoundPage.isVisible = false
        internetErrorPage.isVisible = false
    }

    private fun showNotFoundPage() {
        searchHistory.isVisible = false
        tracksList.isVisible = false
        progressBar.isVisible = false
        notFoundPage.isVisible = true
        internetErrorPage.isVisible = false
    }

    private fun showInternetErrorPage() {
        searchHistory.isVisible = false
        tracksList.isVisible = false
        progressBar.isVisible = false
        notFoundPage.isVisible = false
        internetErrorPage.isVisible = true
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        inputEditText.removeTextChangedListener(null)
    }
}







