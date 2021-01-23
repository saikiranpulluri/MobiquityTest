package com.example.mobiquitytest.utils

import java.math.RoundingMode
import java.text.DecimalFormat

object Utils {
    val df = DecimalFormat("#.##")
    fun convertKelvinToCelsius(kelvinTemp: String?): String {
        return try {
            df.roundingMode = RoundingMode.CEILING
            val minus = kelvinTemp?.toDouble()?.minus(273.15)
            df.format(minus)
        } catch (e: Exception) {
            ""
        }
    }
}