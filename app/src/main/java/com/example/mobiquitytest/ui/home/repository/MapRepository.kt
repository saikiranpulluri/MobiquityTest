package com.example.mobiquitytest.ui.home.repository

import com.example.mobiquitytest.database.City
import com.example.mobiquitytest.database.MobiquityDatabase
import com.google.android.gms.maps.model.LatLng
import timber.log.Timber

class MapRepository(private val database: MobiquityDatabase) {
    suspend fun saveLocationToDB(latLng: LatLng, country: String, city: String, zipCode: String) {
        try {
            val newCity = City(
                pinCode = zipCode,
                cityName = city,
                country = country,
                latLong = latLng.latitude.toString() + "," + latLng.longitude.toString()
            )
            database.cityDao().insert(newCity)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}