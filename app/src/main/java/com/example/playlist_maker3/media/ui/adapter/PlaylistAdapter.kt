package com.example.playlist_maker3.media.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.playlist_maker3.R
import com.example.playlist_maker3.databinding.ItemPlaylistBinding
import com.example.playlist_maker3.media.domain.model.Playlist
import com.example.playlist_maker3.media.setImage

class PlaylistsAdapter(private val clickListener: PlaylistClickListener) :
    RecyclerView.Adapter<PlaylistsViewHolder>() {

    val playlists = ArrayList<Playlist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder {
        return PlaylistsViewHolder(
            ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = playlists.size

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        val playlistItem = playlists[holder.adapterPosition]
        holder.bind(playlistItem)
        holder.itemView.setOnClickListener { clickListener.onTrackClick(playlistItem) }
    }

    fun interface PlaylistClickListener {
        fun onTrackClick(playlist: Playlist)
    }
}

class PlaylistsViewHolder(
    private val binding: ItemPlaylistBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(model: Playlist) {
        val cornerRadius = itemView.resources.getDimensionPixelSize(R.dimen.corner_radius_8)

        binding.namePlaylist.text = model.playlistName
        binding.countTracks.text = itemView.resources.getQuantityString(R.plurals.tracksContOfList, model.tracksCount, model.tracksCount)

        binding.coverPlaylist.setImage(
            url = model.coverImageUrl,
            placeholder = R.drawable.placeholder,
            cornerRadius = cornerRadius,
        )
    }
}