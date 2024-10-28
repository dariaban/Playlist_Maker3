package com.example.playlist_maker3.domain.use_case

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.playlist_maker3.data.repository.SearchHistoryRepository
import com.example.playlist_maker3.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoryInteractor(context: Context):
    SearchHistoryRepository {

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
        val tracks = getTrackHistory().toMutableList()
        tracks.add(0, track)
        tracks.toSet().toList()
        if (tracks.size > 10) {
            tracks.removeLast()
        }
        saveHistory(tracks)
    }
    companion object{
        const val HISTORY_PREFERENCES = "history_preferences"
        const val HISTORY_PREFERENCES_KEY = "key_for_history_preferences"
    }
}
