package com.example.playlist_maker3

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val HISTORY_PREFERENCES = "history_preferences"
const val HISTORY_PREFERENCES_KEY = "key_for_history_preferences"

class SearchActivity : Listener, AppCompatActivity() {
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener
    private lateinit var inputEditText: EditText
    private val tracks = ArrayList<Result>()
    private val history = ArrayList<Result>()


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_search)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val networkError: LinearLayout = findViewById(R.id.network_error)
        val searchError: LinearLayout = findViewById(R.id.search_error)
        val retrofit = Retrofit.Builder().baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ItunesApiService::class.java)
        super.onCreate(savedInstanceState)
        val inputEditText: EditText = findViewById(R.id.search_bar)
        if (savedInstanceState != null) {
            inputEditText.setText(savedInstanceState.getString(EDIT_TEXT_KEY))
        }

        val backButton = findViewById<MaterialButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        val clearButton = findViewById<ImageView>(R.id.clearIcon)
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.isVisible = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {
                hideKeyboard(inputEditText)
            }
        })
        clearButton.setOnClickListener {
            inputEditText.setText("")
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }
        fun searchTrack() {
            val response = retrofit.search(inputEditText.text.toString())
            response.enqueue(object : Callback<MyData> {
                override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                    searchError.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    val dataList = response.body()?.results!!
                    val adapter = TrackAdapter(dataList, this@SearchActivity)
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
                    if (response.code() == 200) {
                        tracks.clear()
                        if (dataList.isNotEmpty()) {
                            tracks.addAll(dataList)
                            adapter.notifyDataSetChanged()
                        }
                    }
                    if (tracks.isEmpty()) {
                        searchError.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }

                }

                override fun onFailure(call: Call<MyData>, t: Throwable) {
                    networkError.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    searchError.visibility = View.GONE
                    val refreshButton = findViewById<MaterialButton>(R.id.refreshButton)
                    refreshButton.setOnClickListener {
                        networkError.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                        searchTrack()
                    }
                }
            })
        }
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchTrack()
                true
            } else false
        }
      val historySharedPreferences = getSharedPreferences(HISTORY_PREFERENCES, MODE_PRIVATE)
                val facts = historySharedPreferences.getString(HISTORY_PREFERENCES_KEY,null)

        if (!history.isNullOrEmpty()||facts!=null) {
                val historyView = findViewById<LinearLayout>(R.id.historyView)
                historyView.visibility = View.VISIBLE
                val historyAdapter = TrackAdapter(createResultListFromJson(facts).toList(), this)
                val historyRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewHistory)
                historyRecyclerView.layoutManager = LinearLayoutManager(this)
                historyRecyclerView.adapter = historyAdapter
                historyAdapter.notifyDataSetChanged()

            }
        val cleanHistory = findViewById<MaterialButton>(R.id.clean_history)
        cleanHistory.setOnClickListener {
            val historyView = findViewById<LinearLayout>(R.id.historyView)
            historyView.visibility = View.GONE
            listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
                if (key == HISTORY_PREFERENCES_KEY) {
                    historySharedPreferences.edit().clear().apply()
                }
            }
            history.clear()
            historySharedPreferences.registerOnSharedPreferenceChangeListener(listener)
            }
        }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EDIT_TEXT_KEY, inputEditText.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString(EDIT_TEXT_KEY).toString()
    }

    private companion object {
        const val EDIT_TEXT_KEY = "EDIT_TEXT_KEY"
    }

    override fun onClick(result: Result) {
        val historyView = findViewById<LinearLayout>(R.id.historyView)
        historyView.visibility = View.VISIBLE
        history.add(0, result)
        if (history.size > 10) {
            history.removeAt(10)
        }
        val historySharedPreferences = getSharedPreferences(HISTORY_PREFERENCES, MODE_PRIVATE)
        historySharedPreferences.edit()
            .putString(HISTORY_PREFERENCES_KEY, createJsonFromResult(history)).apply()
        val historyRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewHistory)
        val historyAdapter = TrackAdapter(
            (createResultListFromJson(
                historySharedPreferences.getString(
                    HISTORY_PREFERENCES_KEY,
                    null
                )
            ).toSet().toList()), this
        )
        historyRecyclerView.adapter = historyAdapter
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyAdapter.notifyDataSetChanged()
    }
    private fun hideKeyboard(view: View){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun createJsonFromResult(result: List<Result>): String {
        return Gson().toJson(result)
    }

    private fun createResultListFromJson(json: String?): Array<Result> {
        return Gson().fromJson(json, Array<Result>::class.java)
    }
    private fun saveHistory(){
        val historySharedPreferences = getSharedPreferences(HISTORY_PREFERENCES, MODE_PRIVATE)
        historySharedPreferences.edit()
            .putString(HISTORY_PREFERENCES_KEY, createJsonFromResult(history)).apply()
    }

    override fun onPause() {
       super.onPause()
       saveHistory()
   }

    override fun onStop() {
        super.onStop()
       saveHistory()
   }
}








