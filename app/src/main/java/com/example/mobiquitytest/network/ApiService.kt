package com.example.mobiquitytest.network

import com.example.mobiquitytest.network.models.CurrentWeatherModel
import com.example.mobiquitytest.network.models.ForecastModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @GET("weather?")
    @Headers("Content-Type: application/json")
    suspend fun getCurrentWeatherForLoc(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("units") units: String): CurrentWeatherModel

    @GET("forecast?")
    @Headers("Content-Type: application/json")
    suspend fun getForeCastForLoc(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("units") units: String): ForecastModel
}