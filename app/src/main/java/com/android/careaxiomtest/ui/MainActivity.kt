package com.android.careaxiomtest.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.careaxiomtest.MainApp
import com.android.careaxiomtest.R
import com.android.careaxiomtest.viewmodel.MainActivityVM
import com.echo.careaxiomtest2.data.model.NetworkState
import com.echo.careaxiomtest2.ui.adapter.PhotoAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        MainApp.appCompnent.inject(this)
        viewModel = ViewModelProviders.of(this)[MainActivityVM::class.java];
        initAdapter()
    }

    fun initAdapter() {
        val gridLayoutManager = GridLayoutManager(this, 1)
        gridLayoutManager.orientation = RecyclerView.VERTICAL
        val userAdapter = PhotoAdapter {
            viewModel.retryRequest()
        }
        recyclerview.layoutManager = gridLayoutManager
        recyclerview.adapter = userAdapter

        viewModel.photos.observe(this, Observer {
            userAdapter.submitList(it)
        })
        viewModel.getNetworkState().observe(this, Observer<NetworkState> { userAdapter.setNetworkState(it) })
    }

}
