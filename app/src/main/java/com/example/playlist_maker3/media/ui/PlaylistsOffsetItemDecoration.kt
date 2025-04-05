package com.example.playlist_maker3.media.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.playlist_maker3.R

class PlaylistsOffsetItemDecoration(
    private val context: Context,
    ) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val offset = context.resources.getDimensionPixelSize(R.dimen.margin_8)
        val leftOffset: Int = offset
        val topOffset: Int = 0
        val rightOffset: Int = offset
        val bottomOffset: Int = offset
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(leftOffset, topOffset, rightOffset, bottomOffset)
    }
}