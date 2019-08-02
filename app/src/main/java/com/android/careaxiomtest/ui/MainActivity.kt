package com.android.careaxiomtest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.careaxiomtest.R
import com.android.careaxiomtest.viewmodel.MainActivityVM

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
