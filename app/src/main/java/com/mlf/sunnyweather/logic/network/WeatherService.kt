package com.mlf.sunnyweather.logic.network

import com.mlf.sunnyweather.base.BaseApp
import com.mlf.sunnyweather.logic.model.DailyResponse
import com.mlf.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @description: TODO 天气服务接口
 * @author: mlf
 * @date: 2024/9/9 19:39
 * @version: 1.0
 */
interface WeatherService {
    @GET("v2.5/${BaseApp.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealtimeResponse>

    @GET("v2.5/${BaseApp.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>

}