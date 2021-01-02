package com.sunnyweather.android.ui.place

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Location
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_preference_place.*


class PreferencePlaceActivity : AppCompatActivity() {

    private lateinit var adapter: PreferencePlaceAdapter
    val viewModel by lazy { ViewModelProviders.of(this).get(PlaceViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference_place)

        var preferenceList:MutableList<Place> = mutableListOf()
        if(viewModel.isPreferencePlacesSaved()) {
            preferenceList = viewModel.getPreferencePlaces().toMutableList()
        }
        addList(viewModel.getLocalPlace().name,preferenceList)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = PreferencePlaceAdapter(this, preferenceList)
        recyclerView.adapter = adapter

        fab.setOnClickListener{
            var PlaceName = ""
            val  inputServer: EditText =  EditText(this)
            val builder: AlertDialog.Builder =  AlertDialog.Builder(this)
            builder.setTitle("Add").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                .setNegativeButton("Cancel", null)
            builder.setPositiveButton("Ok") { _: DialogInterface, _: Int ->
                PlaceName = inputServer.text.toString()
                addList(PlaceName,preferenceList)
                viewModel.savePreferencePlaces(preferenceList)
            }
            builder.show()
        }

        viewModel.placeLiveData.observe(this, Observer{ result ->
            val places = result.getOrNull()
            if (places != null) {
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                val intent = Intent(this, WeatherActivity::class.java).apply {
                    val places = viewModel.placeList
                    putExtra("location_lng", places[0].location.lng)
                    putExtra("location_lat", places[0].location.lat)
                    putExtra("place_name", places[0].name)
                }
                this.startActivity(intent)
            }
        })
    }
     private fun addList(PlaceName: String, PreferenceList:MutableList<Place>){
        var flag = true
        for(it in PreferenceList){
            if(PlaceName == it.name) {
                flag = false
                break
            }
        }
        if(flag)
            PreferenceList.add(Place(PlaceName, Location("",""),""))
    }
}