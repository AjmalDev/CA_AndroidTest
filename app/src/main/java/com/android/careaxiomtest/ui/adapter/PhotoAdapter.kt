package com.echo.careaxiomtest2.ui.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.careaxiomtest.R
import com.android.careaxiomtest.data.Album
import com.android.careaxiomtest.data.Photo
import com.android.careaxiomtest.ui.adapter.ViewHolderAlbum
import com.echo.careaxiomtest2.data.model.NetworkState

class PhotoAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<Any, RecyclerView.ViewHolder>(UserDiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_photos -> ViewHolderPhoto.create(parent)
            R.layout.item_network_progress -> ViewHolderNetwork.create(parent, retryCallback)
            R.layout.item_album_id -> ViewHolderAlbum.create(parent)
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.item_photos -> (holder as ViewHolderPhoto).bindTo(getItem(position) as Photo?)
            R.layout.item_network_progress -> (holder as ViewHolderNetwork).bindTo(networkState)
            R.layout.item_album_id -> (holder as ViewHolderAlbum).bindTo(getItem(position) as Album)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_progress
        } else if (getItem(position) is Photo) {
            Log.i("Test321", "Type returning item -photos")
            R.layout.item_photos
        } else {
            Log.i("Test321", "Type returning item -Albums")
            R.layout.item_album_id
        }
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        if (currentList?.size != 0) {
            val previousState = this.networkState
            val hadExtraRow = hasExtraRow()
            this.networkState = newNetworkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow) {
                    notifyItemRemoved(super.getItemCount())
                } else {
                    notifyItemInserted(super.getItemCount())
                }
            } else if (hasExtraRow && previousState !== newNetworkState) {
                notifyItemChanged(itemCount - 1)
            }
        }
    }

    companion object {
        val UserDiffCallback = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                var id1 = 0
                var id2 = 0
                if (oldItem is Album) {
                    id1 = oldItem.albumId
                } else {
                    val photo = oldItem as Photo
                    id1 = photo.id
                }
                if (newItem is Album) {
                    id2 = newItem.albumId
                } else {
                    val photo = newItem as Photo
                    id2 = photo.id

                }
                return id1 == id2
            }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }
        }

    }

}