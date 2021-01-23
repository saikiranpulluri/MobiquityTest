package com.example.mobiquitytest.ui.forecast

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mobiquitytest.database.City
import com.example.mobiquitytest.database.getDatabase
import com.example.mobiquitytest.network.models.ForecastModel
import com.example.mobiquitytest.ui.home.repository.DashboardRepository

class ForecastViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DashboardRepository(getDatabase(application))

    suspend fun getForecastForCity(city: City): ForecastModel? {
        return repository.getForeCast(city)
    }
}