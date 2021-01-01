package com.sunnyweather.android.ui.place

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.R
import com.sunnyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_preference_place.*
import kotlinx.android.synthetic.main.activity_preference_place.recyclerView
import kotlinx.android.synthetic.main.fragment_place.*

class PreferencePlaceActivity : AppCompatActivity() {

    private lateinit var adapter: PreferencePlaceAdapter
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference_place)

        viewModel.savePreferencePlaces(listOf(viewModel.getLocalPlace()))
        val PreferenceList = viewModel.getPreferencePlaces().toMutableList()
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = PreferencePlaceAdapter(this, PreferenceList)
        recyclerView.adapter = adapter

        viewModel.placeLiveData.observe(this, Observer{ result ->
            val places = result.getOrNull()
            if (places != null) {
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                val intent = Intent(this, WeatherActivity::class.java).apply {
                    val place = viewModel.placeList
                    putExtra("location_lng", place[0].location.lng)
                    putExtra("location_lat", place[0].location.lat)
                    putExtra("place_name", place[0].name)
                }
                this.startActivity(intent)
                adapter.notifyDataSetChanged()
            }
        })
    }
}