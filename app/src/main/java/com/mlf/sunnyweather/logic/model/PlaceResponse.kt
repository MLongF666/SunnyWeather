package com.mlf.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/8 12:45
 * @version: 1.0
 */
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name: String, val location: Location,@SerializedName("formatted_address") val address: String)

data class Location(val lng: String, val lat: String)