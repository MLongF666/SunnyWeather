package com.mlf.sunnyweather.ui.place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.mlf.sunnyweather.logic.Repository
import com.mlf.sunnyweather.logic.model.Place

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/8 13:43
 * @version: 1.0
 */
class PlaceViewModel:ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = searchLiveData.switchMap { query ->
        Log.i("PlaceViewModel", "query: $query")
        Repository.searchPlaces(query)
    }
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavedPlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()

}