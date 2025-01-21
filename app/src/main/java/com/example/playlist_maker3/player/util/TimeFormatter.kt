package com.example.playlist_maker3.player.util

import java.text.SimpleDateFormat
import java.util.Locale

object TimeFormatter {
    private const val TIME_FORMAT = "mm:ss"
    const val ZERO_TIME = "00:00"

    fun format(time: Int): String {
        return SimpleDateFormat(
            TIME_FORMAT,
            Locale.getDefault()
        ).format(time)
    }
}


