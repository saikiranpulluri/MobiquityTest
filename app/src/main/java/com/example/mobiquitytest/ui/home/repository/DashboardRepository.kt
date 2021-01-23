package com.example.mobiquitytest.ui.home.repository

import androidx.lifecycle.LiveData
import com.example.mobiquitytest.database.City
import com.example.mobiquitytest.database.MobiquityDatabase
import com.example.mobiquitytest.network.NetworkApi
import com.example.mobiquitytest.utils.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class DashboardRepository(private val database: MobiquityDatabase) {
    fun getAllCitiesLive(): LiveData<List<City>> {
        return database.cityDao().getAllCitiesLive()
    }

    suspend fun getAllCities(): List<City> {
        return database.cityDao().getAllCities()
    }

    suspend fun getCurrentWeatherInLoc(pincode: String?) {
        withContext(Dispatchers.IO) {
            pincode?.let {
                val city = database.cityDao().getCity(it)
                Timber.d("refresh about is called")
                val split = city.latLong?.split(",")

                split?.let {
                    val weather =
                        NetworkApi.getClient()
                            ?.getCurrentWeatherForLoc(split[0], split[1], Constant.STANDARD)
                    city.temperatureInKelvin = weather?.main?.temp.toString()
                    database.cityDao().insert(city)
                }
            }
        }
    }
}