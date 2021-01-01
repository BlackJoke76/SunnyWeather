package com.sunnyweather.android.ui.place


import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.ui.weather.WeatherActivity

class PreferencePlaceAdapter(private val activity: Activity, private val PreferencePlaceList: List<Place>) : RecyclerView.Adapter<PreferencePlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = PreferencePlaceList[position]
            if (activity is PerferencePlaceActivity)
            {
                activity.viewModel.searchPlaces(place.name)
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
                activity.startActivity(intent)
            }

        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = PreferencePlaceList[position]
        holder.placeName.text = place.name
    }

    override fun getItemCount() = PreferencePlaceList.size

}
