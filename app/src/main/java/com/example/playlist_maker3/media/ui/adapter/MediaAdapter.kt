package com.example.playlist_maker3.media.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlist_maker3.media.ui.FavoritesFragment
import com.example.playlist_maker3.media.ui.PlaylistsFragment

class MediaAdaptor (fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> FavoritesFragment.newInstance()
                1 -> PlaylistsFragment.newInstance()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }
