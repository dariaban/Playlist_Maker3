package com.example.playlist_maker3.search.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlist_maker3.R
import com.example.playlist_maker3.search.domain.model.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TrackAdapter(
    private val clickListener: (Track) -> Unit,
    private val longClickListener: LongTrackClickListener? = null
) :
    RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {
    var tracks = listOf<Track>()

    class TrackViewHolder(parentView: View) : RecyclerView.ViewHolder(parentView) {
        private val trackName: TextView = parentView.findViewById(R.id.trackName)
        private val artistName: TextView = parentView.findViewById(R.id.artistName)
        private val trackTime: TextView = parentView.findViewById(R.id.trackTime)
        private val artworkUrl: ImageView = parentView.findViewById(R.id.trackArtwork)


        fun bind(track: Track) {
            val trackTimeFormat =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
            trackName.text = track.trackName
            artistName.text = track.artistName
            trackTime.text = trackTimeFormat.toString()
            Glide.with(itemView)
                .load(track.artworkUrl100)
                .placeholder(R.drawable.placeholder)
                .transform(RoundedCorners(10))
                .into(artworkUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(tracks[position])
        }

        longClickListener?.let { listener ->
            holder.itemView.setOnLongClickListener {
                listener.onTrackLongClick(tracks[holder.adapterPosition])
                return@setOnLongClickListener true
            }
        }
    }

    fun updateTracks(newTracks: List<Track>) {
        tracks = newTracks
        notifyDataSetChanged()
    }
}


fun interface LongTrackClickListener {
    fun onTrackLongClick(track: Track)
}