package com.example.playlist_maker3.domain.api

import com.example.playlist_maker3.data.repository.DarkTheme

interface DarkThemeInteractor{
     fun checkState(): Boolean
     fun saveTheme(state: Boolean)
}