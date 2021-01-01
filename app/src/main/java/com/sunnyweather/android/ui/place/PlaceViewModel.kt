package com.sunnyweather.android.ui.place

import android.content.Context
import androidx.lifecycle.*
import com.sunnyweather.android.logic.Repository
import com.sunnyweather.android.logic.model.Place

class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun savePreferencePlace(places: List<Place>) = Repository.savePreferencePlace(places);

    fun getPreferencePlace() = Repository.getPreferencePlace();

    fun isPlaceSaved() = Repository.isPlaceSaved()

    fun getLocalPlace() = Repository.getLocalPlace()

}