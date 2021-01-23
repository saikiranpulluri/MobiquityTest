package com.example.mobiquitytest.network

import com.example.mobiquitytest.BuildConfig
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkApi {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Volatile
    private var apiService: ApiService? = null

    fun getClient(): ApiService? {

        if (apiService != null) return apiService

        val build = OkHttpClient.Builder()
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            build.addInterceptor(RequestInterceptor())
            build.addInterceptor(logging)
        }
        val retrofit: Retrofit = Retrofit.Builder()
            .client(build.build())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        apiService = retrofit.create(ApiService::class.java)
        return apiService
    }
}