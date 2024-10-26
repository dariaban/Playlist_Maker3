package com.example.playlist_maker3.domain

import com.example.playlist_maker3.domain.models.Track

interface Listener {
        fun onClick(track: Track)


}
