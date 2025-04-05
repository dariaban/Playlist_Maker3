package com.example.playlist_maker3.media.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.ItemBottomSheetBinding
import com.example.playlist_maker3.media.domain.model.Playlist

class BottomSheetAdapter(private val clickListener: PlaylistClickListener) :
    RecyclerView.Adapter<BottomSheetViewHolder>() {

    val list = ArrayList<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetViewHolder {
        return BottomSheetViewHolder(
            ItemBottomSheetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: BottomSheetViewHolder, position: Int) {
        val playlistItem = list[holder.adapterPosition]
        holder.bind(playlistItem)
        holder.itemView.setOnClickListener { clickListener.onPlaylistClick(playlistItem) }
    }

    fun interface PlaylistClickListener {
        fun onPlaylistClick(playlist: Playlist)
    }
}

class BottomSheetViewHolder(
    private val binding: ItemBottomSheetBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Playlist) {

        binding.namePlaylist.text = model.playlistName
        binding.countTracks.text = itemView.resources.getQuantityString(
            R.plurals.tracksContOfList,
            model.tracksCount,
            model.tracksCount
        )
        model.coverImageUrl?.takeIf { it.isNotEmpty() }?.let { path ->
            Glide.with(itemView)
                .load(path)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .transform(RoundedCorners(itemView.resources.getDimensionPixelSize(R.dimen.corner_radius_2)))
                .into(binding.coverPlaylist)
        } ?: binding.coverPlaylist.setImageResource(R.drawable.placeholder)
    }
}