package com.example.playlist_maker3

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
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.playlist_maker3.Result




import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {
    private lateinit var inputEditText: EditText
    private val tracks = ArrayList<Result>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val inputEditText: EditText = findViewById(R.id.search_bar)
        if (savedInstanceState != null) {
            inputEditText.setText(savedInstanceState.getString(EDIT_TEXT_KEY))
        }

        val backButton = findViewById<MaterialButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
        val inputEditText: EditText = findViewById(R.id.search_bar)
        val clearButton = findViewById<ImageView>(R.id.clearIcon)

        inputEditText.addTextChangedListener(object : TextWatcher {

        fun clearButtonVisibility(s: CharSequence?): Int {
            return if (s.isNullOrEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        clearButton.setOnClickListener {
            inputEditText.setText("")
        }
        val simpleTextWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


        clearButton.setOnClickListener {
            inputEditText.setText("")
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }

        inputEditText.addTextChangedListener(simpleTextWatcher)

        val adapter = TrackAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val networkError = findViewById<LinearLayout>(R.id.network_error)
        val searchError = findViewById<LinearLayout>(R.id.search_error)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesApiService::class.java)

        fun searchTrack() {
            val response = retrofit.search(inputEditText.text.toString())
            response.enqueue(object : Callback<MyData> {
                override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                    searchError.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    val dataList = response.body()?.results!!
                    val adapter = TrackAdapter(dataList)
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


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(EDIT_TEXT_KEY, inputEditText.text.toString())

        inputEditText.setText(savedValue)
    }


    private var savedValue: String = DEF_VALUE
    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_VALUE, savedValue)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString(EDIT_TEXT_KEY).toString()

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
    }



    private companion object {
        const val EDIT_TEXT_KEY = "EDIT_TEXT_KEY"

    companion object {
        const val SEARCH_VALUE = "SEARCH_VALUE"
        const val DEF_VALUE = " "

    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedValue = savedInstanceState.getString(SEARCH_VALUE, DEF_VALUE)

    }

}
