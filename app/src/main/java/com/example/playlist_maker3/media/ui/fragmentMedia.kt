package com.example.playlist_maker3.media.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.FragmentMediaBinding
import com.example.playlist_maker3.media.ui.adapter.MediaAdaptor
import com.google.android.material.tabs.TabLayoutMediator

class MediaFragment : Fragment() {

    private lateinit var mediaAdapter: MediaAdaptor
    private var _binding: FragmentMediaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaBinding.inflate(inflater, container, false)

        setupViewPager()

        binding.backButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        return binding.root
    }

    private fun setupViewPager() {
        mediaAdapter = MediaAdaptor(childFragmentManager, lifecycle)

        binding.viewPager.adapter = mediaAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_favorites)
                1 -> getString(R.string.tab_playlists)
                else -> ""
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

