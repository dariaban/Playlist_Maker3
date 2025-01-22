package com.example.playlist_maker3.settings.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.playlist_maker3.databinding.ActivitySettingsBinding
import com.example.playlist_maker3.settings.domain.DarkThemeState


class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(
            this, SettingsActivityViewModel.getViewModelFactory()

        )[SettingsActivityViewModel::class.java]
        viewModel.getTheme()
        viewModel.observeState().observe(this) { state ->
            when (state) {
                DarkThemeState.Dark -> binding.buttonSwitch.isChecked = true
                DarkThemeState.Bright -> binding.buttonSwitch.isChecked = false
            }
        }
        binding.buttonSwitch.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                viewModel.makeItDark()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewModel.makeItBright()
            }
        }

        binding.backButton.setOnClickListener { finish() }
        binding.shareButton.setOnClickListener { viewModel.share() }
        binding.buttonSupport.setOnClickListener { viewModel.support() }
        binding.buttonAgreement.setOnClickListener { viewModel.open() }

    }
}




