package com.echo.careaxiomtest2.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.careaxiomtest.R
import com.echo.careaxiomtest2.data.model.NetworkState
import com.echo.careaxiomtest2.data.model.Status
import kotlinx.android.synthetic.main.item_network_progress.view.*

class ViewHolderNetwork(val view: View, private val retryCallback: () -> Unit) : RecyclerView.ViewHolder(view) {

    init {
        itemView.retryLoadingButton.setOnClickListener { retryCallback() }
    }

    fun bindTo(networkState: NetworkState?) {
        //error message
        itemView.errorMessageTextView.visibility = if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            itemView.errorMessageTextView.text = networkState.message
        }
//        loading and retry
        itemView.retryLoadingButton.visibility = if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE
        itemView.loadingProgressBar.visibility = if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE
        itemView.retryLoadingButton.setOnClickListener {
            retryCallback()
        }
    }

    companion object {
        fun create(parent: ViewGroup, retryCallback: () -> Unit): ViewHolderNetwork {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_network_progress, parent, false)
            return ViewHolderNetwork(view, retryCallback)
        }
    }


}