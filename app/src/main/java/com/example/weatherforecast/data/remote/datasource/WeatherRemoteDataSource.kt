package com.example.weatherforecast.data.remote.datasource

import android.util.Log.e
import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.remote.network.WeatherApiClient
import com.example.weatherforecast.data.remote.response.ForecastResponse
import com.example.weatherforecast.data.remote.response.WeatherResponse
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor() {

    private val weatherService = WeatherApiClient.weatherService

    suspend fun getCurrentWeather(lat: Double, long: Double): Result<WeatherResponse> {
        return try {
            val response = weatherService.getCurrentWeather(lat, long, BuildConfig.WEATHER_API_KEY)

            if (response.isSuccessful) {

                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))

            } else {
                Result.failure(
                    Exception("API Error: ${response.code()} - ${response.message()}")
                )
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getForecastWeather(lat: Double, long: Double): Result<ForecastResponse> {
        return try {
            val response = weatherService.getForecastWeather(lat, long, BuildConfig.WEATHER_API_KEY)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(
                    Exception("API Error: ${response.code()} - ${response.message()}")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}