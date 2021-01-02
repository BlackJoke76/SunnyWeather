package com.sunnyweather.android.ui.place


import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.R
import com.sunnyweather.android.logic.model.Place

class PreferencePlaceAdapter(private val activity: Activity, private val PreferencePlaceList: MutableList<Place>) : RecyclerView.Adapter<PreferencePlaceAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = PreferencePlaceList.elementAt(position)
            if (activity is PreferencePlaceActivity)
                    activity.viewModel.searchPlaces(place.name)

        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = PreferencePlaceList.elementAt(position)
        holder.placeName.text = place.name
    }

    override fun getItemCount() = PreferencePlaceList.size

}
