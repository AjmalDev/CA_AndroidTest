package com.android.careaxiomtest.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.careaxiomtest.R
import com.android.careaxiomtest.data.Album
import kotlinx.android.synthetic.main.item_album_id.view.*

class ViewHolderAlbum(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(album: Album) {
        itemView.albumId.text = "Album " + album.albumId
    }

    companion object {
        fun create(parent: ViewGroup): ViewHolderAlbum {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_album_id, parent, false)
            return ViewHolderAlbum(view)
        }
    }
}
