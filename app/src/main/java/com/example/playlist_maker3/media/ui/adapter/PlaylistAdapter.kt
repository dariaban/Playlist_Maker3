package com.example.playlist_maker3.media.ui.adapter

import android.os.Environment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlist_maker3.Constants
import com.example.playlist_maker3.R
import com.example.playlist_maker3.media.domain.model.Playlist
import java.io.File


abstract class PlaylistsAdapter(private val clickListener: PlaylistClickListener) :
    RecyclerView.Adapter<PlaylistsViewHolder>() {
    var playlists = listOf<Playlist>()
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder
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
class PlaylistsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val playlistCover: ImageView = itemView.findViewById(R.id.cover_playlist)
    private val playlistName: TextView = itemView.findViewById(R.id.name_playlist)
    private val tracksCount: TextView = itemView.findViewById(R.id.count_tracks)

    fun bind(playlist: Playlist) {
        playlistName.text = playlist.name
        tracksCount.text = tracksCount.resources.getQuantityString(
            R.plurals.tracksContOfList, playlist.tracksCount, playlist.tracksCount
        )

        val cornerRadius =
            itemView.resources.getDimensionPixelSize(R.dimen.corner_radius_8)


        val filePath = File(
            itemView.context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            Constants.PLAYLISTS_IMAGES
        )
        Glide
            .with(itemView)
            .load(playlist.cover?.let { imageName -> File(filePath, imageName) })
            .placeholder(R.drawable.placeholder_512)
            .transform(CenterCrop(), RoundedCorners(cornerRadius))
            .into(playlistCover)

    }

}