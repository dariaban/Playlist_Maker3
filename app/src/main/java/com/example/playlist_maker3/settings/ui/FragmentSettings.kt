package com.example.playlist_maker3.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.playlist_maker3.databinding.FragmentSettingsBinding
import com.example.playlist_maker3.settings.domain.DarkThemeState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentSettings : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsActivityViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()

        viewModel.getTheme()
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                DarkThemeState.Dark -> binding.buttonSwitch.isChecked = true
                DarkThemeState.Bright -> binding.buttonSwitch.isChecked = false
            }
        }

        binding.buttonSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                viewModel.makeItDark()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewModel.makeItBright()
            }
        }


        binding.shareButton.setOnClickListener {
            viewModel.share()
        }


        binding.buttonSupport.setOnClickListener {
            viewModel.support()
        }


        binding.buttonAgreement.setOnClickListener {
            viewModel.open()
        }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = binding.searchBackbuttonToolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}