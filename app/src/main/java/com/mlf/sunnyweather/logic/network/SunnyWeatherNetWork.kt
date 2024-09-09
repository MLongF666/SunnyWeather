package com.mlf.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/8 13:09
 * @version: 1.0
 */
object SunnyWeatherNetWork {

    private val placeService = ServiceCreator.create<PlaceService>()
    private val weatherService = ServiceCreator.create<WeatherService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()
    suspend fun getRealtimeWeather(lng: String, lat: String) = weatherService.getRealtimeWeather(lng, lat).await()
    suspend fun getDailyWeather(lng: String, lat: String) = weatherService.getDailyWeather(lng, lat).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            continuation -> enqueue(object : retrofit2.Callback<T> {
            override fun onResponse(p0: Call<T>, p1: Response<T>) {
                val body = p1.body()
                if (body!= null){
                    continuation.resume(body)
                }else{
                    continuation.resumeWithException(Exception("response body is null"))
                }
            }
            override fun onFailure(p0: Call<T>, p1: Throwable) {
                continuation.resumeWithException(p1)
            }
        })
        }

    }
}