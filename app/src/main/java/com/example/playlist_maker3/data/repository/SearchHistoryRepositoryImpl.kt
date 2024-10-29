package com.example.playlist_maker3.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.playlist_maker3.Creator
import com.example.playlist_maker3.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchHistoryRepositoryImpl(context: Context): SearchHistoryRepository {
    private val historySharedPreferences: SharedPreferences = context.getSharedPreferences(
        HISTORY_PREFERENCES,
        MODE_PRIVATE
    )
    override fun getTrackHistory(): ArrayList<Track> {
        val json = historySharedPreferences.getString(HISTORY_PREFERENCES_KEY, null)
            ?: return arrayListOf()
        return Gson().fromJson(json, object : TypeToken<ArrayList<Track>>() {}.type)
    }

    override fun clearHistory() {
        historySharedPreferences.edit().clear().apply()
    }

    override fun saveHistory(history: MutableList<Track>) {
        val json = Gson().toJson(history)
        historySharedPreferences.edit().putString(HISTORY_PREFERENCES_KEY, json).apply()
    }
    override fun addTrack(track: Track) {
        getTrackHistory().add(0, track)
        getTrackHistory().toSet().toList()
        if (getTrackHistory().size > 10) {
            getTrackHistory().removeLast()
        }
        saveHistory(getTrackHistory())
    }
    companion object{
        const val HISTORY_PREFERENCES = "history_preferences"
        const val HISTORY_PREFERENCES_KEY = "key_for_history_preferences"
    }
}