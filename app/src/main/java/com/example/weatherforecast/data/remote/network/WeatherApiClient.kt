package com.example.weatherforecast.data.remote.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java


object WeatherApiClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Volatile
    private var retrofit: Retrofit? = null
    private var gson = GsonBuilder().serializeNulls().create()

    private fun getRetrofitInstance(): Retrofit {
        return retrofit ?: synchronized(this) {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(gson)
                )
                .build()
                .also { retrofit = it }
        }
    }

    val weatherService: WeatherApiService by lazy {
        getRetrofitInstance().create(WeatherApiService::class.java)
    }
}