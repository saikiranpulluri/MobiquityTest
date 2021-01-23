package com.example.mobiquitytest.ui.home.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mobiquitytest.database.getDatabase
import com.example.mobiquitytest.ui.home.repository.MapRepository
import com.google.android.gms.maps.model.LatLng

class MapViewModel(application: Application) : AndroidViewModel(application) {
    private val mapRepository = MapRepository(getDatabase(application))

    suspend fun saveLocationToDB(latLng: LatLng, country: String, city: String, zipCode: String) {
        mapRepository.saveLocationToDB(latLng, country, city, zipCode)
    }
}