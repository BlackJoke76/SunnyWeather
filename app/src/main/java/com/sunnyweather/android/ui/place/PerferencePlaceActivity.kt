package com.sunnyweather.android.ui.place

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
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

    //    viewModel.savePreferencePlaces(listOf(viewModel.getLocalPlace()))
        var PreferenceList:List<Place> = listOf()
        if(viewModel.isPreferencePlacesSaved()) {
            PreferenceList = viewModel.getPreferencePlaces().toMutableList()
        }
        PreferenceList += viewModel.getLocalPlace()
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = PreferencePlaceAdapter(this, PreferenceList)
        recyclerView.adapter = adapter

        fab.setOnClickListener{
            var PlaceName = "";
            val  inputServer: EditText =  EditText(this);
            val builder: AlertDialog.Builder =  AlertDialog.Builder(this);
            builder.setTitle("Add").setIcon(android.R.drawable.ic_dialog_info).setView(inputServer)
                .setNegativeButton("Cancel", null);
            builder.setPositiveButton("Ok") { _: DialogInterface, i: Int ->
                PlaceName = inputServer.text.toString();
                PreferenceList += Place(PlaceName, Location("", ""), "")
                viewModel.savePreferencePlaces(PreferenceList)
                adapter.notifyDataSetChanged()
            };
            adapter.notifyDataSetChanged()
            builder.show();
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.number_item -> Toast.makeText(this,"B20180302114", Toast.LENGTH_SHORT).show()
            R.id.name_item -> Toast.makeText(this,"朱家伟", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}