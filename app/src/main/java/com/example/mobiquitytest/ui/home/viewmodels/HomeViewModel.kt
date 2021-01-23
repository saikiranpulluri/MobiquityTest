package com.example.mobiquitytest.ui.home.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mobiquitytest.database.City
import com.example.mobiquitytest.database.getDatabase
import com.example.mobiquitytest.ui.home.repository.DashboardRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DashboardRepository(getDatabase(application))

    fun getAllCitiesLive(): LiveData<List<City>> {
        return repository.getAllCitiesLive()
    }

    suspend fun getAllCities(): List<City> {
        return repository.getAllCities()
    }
}