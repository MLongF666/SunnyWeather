package com.mlf.sunnyweather.logic



import androidx.lifecycle.liveData
import com.mlf.sunnyweather.logic.dao.PlaceDao
import com.mlf.sunnyweather.logic.model.Place
import com.mlf.sunnyweather.logic.model.Weather

import com.mlf.sunnyweather.logic.network.SunnyWeatherNetWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext


/**
 * @description: TODO 数据仓库
 * @author: mlf
 * @date: 2024/9/8 13:18
 * @version: 1.0
 */
object Repository {
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        val result = SunnyWeatherNetWork.searchPlaces(query)
        if (result.status == "ok"){
            val places = result.places
            Result.success(places)
        }else{
            Result.failure(RuntimeException("response status is ${result.status}"))
        }
    }

    fun refreshWeather(lng: String, lat: String,placeName:String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealTime =async {
                SunnyWeatherNetWork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetWork.getDailyWeather(lng, lat)
            }
            val realTimeResponse = deferredRealTime.await()
            val dailyResponse = deferredDaily.await()
            if (realTimeResponse.status == "ok" && dailyResponse.status == "ok"){
                val weather = Weather(realTimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            }else{
                Result.failure(RuntimeException("response status is ${realTimeResponse.status},${dailyResponse.status}"))
            }
            }
        }
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}