package com.example.mobiquitytest.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

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

    fun dayConverter(time: Long): String {
        val converter = SimpleDateFormat("EEE, d MMM yyyy hh:mm a", Locale.ENGLISH)
        return converter.format(Date(time * 1000))
    }
}