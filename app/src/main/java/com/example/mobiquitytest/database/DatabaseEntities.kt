package com.example.mobiquitytest.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "city")
data class City(
    @NonNull
    @PrimaryKey
    var pinCode: String,
    var cityName: String? = "",
    var country: String? = "",
    var latLong: String? = "",
    var temperatureInKelvin: String? = ""
) : Serializable
