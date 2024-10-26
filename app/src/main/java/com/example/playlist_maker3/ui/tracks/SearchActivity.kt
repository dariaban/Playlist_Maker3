package com.example.playlist_maker3.ui.tracks

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlist_maker3.Creator
import com.example.playlist_maker3.domain.Listener
import com.example.playlist_maker3.R
import com.example.playlist_maker3.domain.api.TracksInteractor
import com.example.playlist_maker3.domain.models.Track
import com.google.android.material.button.MaterialButton


const val HISTORY_PREFERENCES = "history_preferences"
const val HISTORY_PREFERENCES_KEY = "key_for_history_preferences"

const val LAST_TRACK_KEY = "key_for_last_track"
private const val SEARCH_DEBOUNCE_DELAY = 2000L
private const val CLICK_DEBOUNCE_DELAY = 1000L
private var isClickAllowed = true
private val handler = Handler(Looper.getMainLooper())


class SearchActivity : Listener, AppCompatActivity() {
    private val tracks = ArrayList<Track>()
    private var history = ArrayList<Track>()
    private lateinit var inputEditText: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var historyView: LinearLayout
    private lateinit var networkError: LinearLayout
    private lateinit var searchError: LinearLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var backButton: MaterialButton
    private lateinit var clearButton: ImageView
    private lateinit var getHistoryRepository: Creator
    private lateinit var historySharedPreferences: SharedPreferences
    private lateinit var historyAdapter: TrackAdapter
    private lateinit var cleanHistoryButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        recyclerView = findViewById(R.id.recyclerView)
        historyRecyclerView = findViewById(R.id.recyclerViewHistory)
        networkError = findViewById(R.id.network_error)
        searchError = findViewById(R.id.search_error)
        progressBar = findViewById(R.id.progress_circular)
        backButton = findViewById(R.id.backButton)
        clearButton = findViewById(R.id.clearIcon)
        inputEditText = findViewById(R.id.search_bar)
        historyView = findViewById(R.id.historyView)
        cleanHistoryButton = findViewById(R.id.clean_history)



        if (savedInstanceState != null) {
            inputEditText.setText(savedInstanceState.getString(EDIT_TEXT_KEY))
        }
        backButton.setOnClickListener {
            finish()
        }
        getSharedHistory()

        val searchRunnable = Runnable { searchTrack() }
        fun searchDebounce() {
            handler.removeCallbacks(searchRunnable)
            handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
        }
        clearButton.setOnClickListener {
            inputEditText.setText("")
        }
        inputEditText.setOnEditorActionListener()
        { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack()
                true
            } else false
        }

        inputEditText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    clearButton.isVisible = !s.isNullOrEmpty()
                    searchDebounce()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
        inputEditText.setOnFocusChangeListener()
        { view, hasFocus ->
            val historyView = findViewById<LinearLayout>(R.id.historyView)
            historyView.visibility = View.GONE
            hideKeyboard(inputEditText)
        }
        cleanHistoryButton.setOnClickListener {
            getHistoryRepository.provideSearchHistoryRepository(historySharedPreferences)
                .clearHistory()
            historyAdapter = TrackAdapter(history, this)
            historyAdapter.notifyDataSetChanged()
            showEmptyPage()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val inputEditText: EditText = findViewById(R.id.search_bar)
        outState.putString(EDIT_TEXT_KEY, inputEditText.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString(EDIT_TEXT_KEY).toString()
        historyView.visibility = View.VISIBLE
    }

    private companion object {
        const val EDIT_TEXT_KEY = "EDIT_TEXT_KEY"
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    override fun onClick(track: Track) {
        if (clickDebounce()) {
            getHistoryRepository.provideSearchHistoryRepository(historySharedPreferences)
                .addTrack(track)
            historyAdapter = TrackAdapter(getHistoryRepository.provideSearchHistoryRepository(historySharedPreferences).getTrackHistory(), this)
            historyAdapter.notifyDataSetChanged()
            val playerActivityIntent = Intent(this, PlayerActivity::class.java)
            playerActivityIntent.putExtra(LAST_TRACK_KEY, track)
            startActivity(playerActivityIntent)
        }
    }

    private fun getSharedHistory() {
        getHistoryRepository = Creator
        historySharedPreferences = getSharedPreferences(HISTORY_PREFERENCES, MODE_PRIVATE)
        getHistoryRepository.provideSearchHistoryRepository(historySharedPreferences)
        history =
            getHistoryRepository.provideSearchHistoryRepository(historySharedPreferences)
                .getTrackHistory()
        val lastTrack = historySharedPreferences.getString(HISTORY_PREFERENCES_KEY, null)
        if (history.isNotEmpty() || !lastTrack.isNullOrEmpty()) {
            historyView.visibility = View.VISIBLE
            historyAdapter = TrackAdapter(history, this)
            historyRecyclerView.layoutManager = LinearLayoutManager(this)
            historyRecyclerView.adapter = historyAdapter
            historyAdapter.notifyDataSetChanged()
        } else {
            showEmptyPage()
        }


        SharedPreferences.OnSharedPreferenceChangeListener { _: SharedPreferences, key: String? ->
            if (key == HISTORY_PREFERENCES_KEY) {
                historyAdapter = TrackAdapter(history, this)
                historyAdapter.notifyDataSetChanged()
            }
        }
    }


    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)

    }


    private fun showTracks() {
        networkError.visibility = View.GONE
        searchError.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
        historyView.visibility = View.GONE
    }

    private fun showNetworkError() {
        networkError.visibility = View.VISIBLE
        searchError.visibility = View.GONE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        historyView.visibility = View.GONE
    }

    private fun showSearchError() {
        networkError.visibility = View.GONE
        searchError.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        historyView.visibility = View.GONE
    }

    private fun showProgressBar() {
        networkError.visibility = View.GONE
        searchError.visibility = View.GONE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        historyView.visibility = View.GONE
    }

    private fun showEmptyPage() {
        networkError.visibility = View.GONE
        searchError.visibility = View.GONE
        recyclerView.visibility = View.GONE
        progressBar.visibility = View.GONE
        historyView.visibility = View.GONE
    }

    private fun searchTrack() {
        showProgressBar()
        Creator.provideTracksInteractor().searchTracks(
            inputEditText.text.toString(),
            object : TracksInteractor.TracksConsumer {
                override fun consume(foundTrack: List<Track>) {
                    runOnUiThread {
                        if (foundTrack.isNotEmpty()) {
                            tracks.clear()
                            tracks.addAll(foundTrack)
                            val adapter = TrackAdapter(tracks, this@SearchActivity)
                            recyclerView.layoutManager =
                                LinearLayoutManager(this@SearchActivity)
                            recyclerView.adapter = adapter
                            adapter.notifyDataSetChanged()
                            showTracks()
                        } else {
                            showSearchError()
                        }
                    }
                }

                override fun onFailure(t: Throwable) {
                    runOnUiThread {
                        showNetworkError()
                    }
                }
            })
    }
}












