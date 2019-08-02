package com.echo.careaxiomtest2.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.careaxiomtest.R
import com.android.careaxiomtest.data.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.item_photos.view.*

class ViewHolderPhoto(view: View) : RecyclerView.ViewHolder(view) {

    fun bindTo(photo: Photo?) {
        itemView.tvPhotoTitle.text = photo?.title

        Glide.with(itemView.context).load(photo?.thumbnails).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).into(itemView.imgPhoto);
    }

    companion object {
        fun create(parent: ViewGroup): ViewHolderPhoto {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_photos, parent, false)
            return ViewHolderPhoto(view)
        }
    }

}