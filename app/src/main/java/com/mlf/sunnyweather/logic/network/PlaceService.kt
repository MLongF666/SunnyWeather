package com.mlf.sunnyweather.logic.network

import com.mlf.sunnyweather.base.BaseApp
import com.mlf.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @description: TODO
 * @author: mlf
 * @date: 2024/9/8 12:47
 * @version: 1.0
 */
interface PlaceService {

    @GET("v2/place?token=${BaseApp.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>


}