package com.example.playlist_maker3.media.ui

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.BottomSheetBinding
import com.example.playlist_maker3.media.domain.BottomSheetState
import com.example.playlist_maker3.media.ui.adapter.BottomSheetAdapter
import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.search.domain.model.Track
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import com.google.android.material.R.id.design_bottom_sheet
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.serialization.encodeToString

class PlaylistsBottomSheet : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetBinding
    private val viewModel by viewModel<BottomSheetViewModel>()
    private lateinit var playlistsAdapter: BottomSheetAdapter
    private lateinit var track: Track


    override fun onStart() {
        super.onStart()
        setupRatio(requireContext(), dialog as BottomSheetDialog, 100)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        track = requireArguments()
            .getString(Constants.TRACK)
            ?.let { Json.decodeFromString<Track>(it) } !!
        Log.d("Track_item", track.toString())
        initAdapter()
        initBtnCreate()
        initObserver()
    }

    private fun initAdapter() {
        playlistsAdapter = BottomSheetAdapter { playlist ->
            viewModel.onPlaylistClicked(playlist, track)
        }
        binding.playlistsRecycler.adapter = playlistsAdapter
    }

    private fun initBtnCreate() {
        binding.createPlaylistBtn.setOnClickListener {
            findNavController().navigate(
                R.id.action_bottomSheet_to_newPlaylist
            )
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.contentFlow.collect { screenState ->
                render(screenState)
            }
        }
    }

    private fun render(state: BottomSheetState) {
        when (state) {
            is BottomSheetState.AddedAlready -> {
                val message =
                    getString(R.string.already_added) + " \"" + state.playlistModel.playlistName + "\" "
                Toast
                    .makeText(requireContext(), message, Toast.LENGTH_SHORT)
                    .show()
            }

            is BottomSheetState.AddedNow -> {
                val message =
                    getString(R.string.added) + " \"" + state.playlistModel.playlistName + "\" "

                showMessage(message)
                dialog?.cancel()
            }

            else -> showContent(state.content)
        }
    }

    private fun showMessage(message: String) {
        Snackbar
            .make(
                requireContext(),
                requireActivity().findViewById(R.id.container),
                message,
                Snackbar.LENGTH_SHORT
            )
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.bar))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.bar_text))
            .setDuration(Constants.MESSAGE_DURATION_MILLIS)
            .show()
    }

    private fun showContent(content: List<Playlist>) {
        binding.playlistsRecycler.visibility = View.VISIBLE
        playlistsAdapter.apply {
            list.clear()
            list.addAll(content)
            notifyDataSetChanged()
        }
    }

    private fun setupRatio(context: Context, bottomSheetDialog: BottomSheetDialog, percetage: Int) {

        val bottomSheet = bottomSheetDialog.findViewById<View>(design_bottom_sheet) as FrameLayout
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight(context, percetage)
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

    private fun getBottomSheetDialogDefaultHeight(context: Context, percetage: Int): Int {
        return getWindowHeight(context) * percetage / 100
    }

    private fun getWindowHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()

        @Suppress("DEPRECATION") requireActivity().windowManager.defaultDisplay.getMetrics(
            displayMetrics
        )

        return displayMetrics.heightPixels
    }

    companion object {
        fun createArgs(track: Track): Bundle = bundleOf(
            Constants.TRACK to Json.encodeToString(track)

        )
    }
}