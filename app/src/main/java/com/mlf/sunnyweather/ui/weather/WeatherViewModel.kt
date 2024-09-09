package com.mlf.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.mlf.sunnyweather.logic.Repository
import com.mlf.sunnyweather.logic.model.Location

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/9 20:09
 * @version: 1.0
 */
class WeatherViewModel:ViewModel(){

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = locationLiveData.switchMap { location ->
        Repository.refreshWeather(location.lng, location.lat, placeName)
    }

    fun refreshWeather(lng: String, lat: String) {
        locationLiveData.value = Location(lng, lat)
    }

}