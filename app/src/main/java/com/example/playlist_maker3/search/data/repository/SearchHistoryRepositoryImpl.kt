package com.example.playlist_maker3.search.data.repository

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.playlist_maker3.search.domain.api.SearchHistoryRepository
import com.example.playlist_maker3.search.domain.model.Track
import com.google.gson.Gson

class SearchHistoryRepositoryImpl(private val historySharedPreferences: SharedPreferences) : SearchHistoryRepository {

    override fun getTrackHistory(): List<Track> {
        val json = historySharedPreferences.getString(HISTORY_PREFERENCES_KEY, null)
            ?: return arrayListOf()
        return ArrayList(Gson().fromJson(json, Array<Track>::class.java).toList())
    }

    override fun clearHistory() {
        historySharedPreferences.edit()
            .remove(HISTORY_PREFERENCES_KEY)
            .apply()
    }

    override fun saveHistory(history: List<Track>) {
        val json = Gson().toJson(history)
        historySharedPreferences.edit().putString(HISTORY_PREFERENCES_KEY, json).apply()
    }

    override fun addTrack(track: Track) {
        val tracksFromSearchHistory = getTrackHistory().toMutableList()
        tracksFromSearchHistory.removeIf { it.trackId == track.trackId }
        if (tracksFromSearchHistory.size >= 10) {
            tracksFromSearchHistory.removeAt(tracksFromSearchHistory.size - 1)
        }
        tracksFromSearchHistory.add(0, track)
        historySharedPreferences.edit()
            .putString(HISTORY_PREFERENCES_KEY, Gson().toJson(tracksFromSearchHistory))
            .apply()
    }

    companion object {
        const val HISTORY_PREFERENCES = "history_preferences"
        const val HISTORY_PREFERENCES_KEY = "key_for_history_preferences"
    }
}