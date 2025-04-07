package com.example.playlist_maker3.media.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.FragmentNewPlaylistBinding
import com.example.playlist_maker3.media.domain.model.Playlist
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


open class NewPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentNewPlaylistBinding
    open val viewModel by viewModel<NewPlaylistViewModel>()
    private var playlist: Playlist? = null
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPlaylist()

        if(!binding.playlistName.text.isNullOrEmpty()){
            binding.buttonCreate.setBackgroundResource(R.drawable.rounder2)
        }

        val nav = activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        if (nav != null) {
            nav.visibility = View.GONE
        }

        binding.playlistName.doOnTextChanged { s: CharSequence?, _, _, _ ->
            binding.buttonCreate.isEnabled = !s.isNullOrEmpty()
            renderBoxStrokeEditTextColor( binding.playlistNameContainer, s)
            if (!binding.playlistName.text.isNullOrEmpty()) {
                binding.buttonCreate.setBackgroundResource(R.drawable.rounder2)
            } else {
                binding.buttonCreate.setBackgroundResource(R.drawable.rounder)
            }
        }

        binding.playlistDescription.doOnTextChanged { s: CharSequence?, _, _, _ ->
            renderBoxStrokeEditTextColor( binding.playlistDescriptionContainer, s)
        }

        binding.buttonCreate.setOnClickListener {
            if (!viewModel.isClickable)
                return@setOnClickListener
            viewModel.onBtnClick()

            val name = binding.playlistName.text.toString()
            val description = binding.playlistDescription.text.toString()
            if (playlist != null) {
                viewModel.updatePlaylist(
                    playlist!!.playlistId,
                    name = name,
                    description = description,
                    imageUri = imageUri
                ) {
                    findNavController().popBackStack()
                }
            } else {
                viewModel.createPlaylist(
                    name = name,
                    description = description,
                    imageUri = imageUri
                ) {
                    Toast.makeText(
                        requireContext(),
                        String.format(
                            resources.getText(R.string.playlist_created).toString(),
                            name
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }

            }
        }

        initToolbar()
        initPickMediaRegister()
        initBackPressed()

    }

    @Suppress("DEPRECATION")
    override fun onAttach(context: Context) {
        super.onAttach(context)
        playlist = arguments?.getSerializable(Constants.PLAYLIST) as Playlist?

    }

    private fun initPlaylist() {
        playlist?.let {
            binding.toolbar.title = getString(R.string.edit_title)
            binding.buttonCreate.text = getString(R.string.save_playlist)
            binding.playlistName.setText(it.name)
            binding.playlistDescription.setText(it.description)
            it.cover?.let { imageName ->
                binding.playlistCoverImage.setImageURI(
                    File(
                        File(
                            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                            Constants.PLAYLISTS_IMAGES
                        ), imageName
                    ).toUri()
                )
            }
            binding.buttonCreate.isEnabled = true
        }
    }

    private fun initBackPressed() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (checkInput()) {
                    showDialog()
                } else {
                    findNavController().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun initPickMediaRegister() {
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    binding.playlistCoverImage.setImageURI(uri)
                    imageUri = uri
                }
            }
        binding.playlistCoverImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun checkInput(): Boolean {
        playlist?.let {
            return false
        }
        return (
                imageUri != null
                        || binding.playlistName.text.toString().isNotEmpty()
                        || binding.playlistDescription.text.toString().isNotEmpty()
                )
    }

    private fun initToolbar() {
        binding.toolbar.setOnClickListener {
            if (checkInput()) {
                showDialog()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    private fun renderBoxStrokeEditTextColor(
        view: com.google.android.material.textfield.TextInputLayout,
        text: CharSequence?
    ) {
        if (!text.isNullOrEmpty()) {
            view.defaultHintTextColor = ContextCompat.getColorStateList(
                requireContext(),
                R.color.blue
            )
            ContextCompat
                .getColorStateList(requireContext(), R.color.blue)
                ?.let { view.setBoxStrokeColorStateList(it) }
        } else {
            view.defaultHintTextColor = ContextCompat.getColorStateList(
                requireContext(),
                R.color.gray
            )
            ContextCompat
                .getColorStateList(requireContext(), R.color.gray)
                ?.let { view.setBoxStrokeColorStateList(it) }
        }
    }


    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext(), R.style.CustomMaterialDialog)
            .setTitle(getString(R.string.title_playlist_dialog))
            .setMessage(getString(R.string.message_playlist_dialog))
            .setNeutralButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.complete)) { _, _ -> goBack() }
            .show()
    }

    private fun goBack() {
        findNavController().navigateUp()
    }
}

