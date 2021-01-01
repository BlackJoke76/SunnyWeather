package com.sunnyweather.android.ui.place

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.R
import kotlinx.android.synthetic.main.activity_perference_place.*

class PerferencePlaceActivity : AppCompatActivity() {

    private lateinit var adapter: PreferencePlaceAdapter
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perference_place)


        val PreferenceList = viewModel.getPreferencePlace()
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }
}