package com.mlf.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/9 19:42
 * @version: 1.0
 */
class RealtimeResponse(val status: String, val result: Result) {

    class Result(val realtime: Realtime)

    class Realtime(val skycon: String, val temperature: Float, @SerializedName("air_quality") val airQuality: AirQuality)

    class AirQuality(val aqi: AQI)

    class AQI(val chn: Float)

}