package com.example.weatherforecast.data.remote.datasource

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.local.datastore.toApiLang
import com.example.weatherforecast.data.local.datastore.toApiUnits
import com.example.weatherforecast.data.remote.network.WeatherApiService
import com.example.weatherforecast.data.remote.response.ForecastResponse
import com.example.weatherforecast.data.remote.response.WeatherResponse
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val weatherService: WeatherApiService, private val appDataStore: AppDataStore
) {
    suspend fun getCurrentWeather(lat: Double, long: Double): Result<WeatherResponse> {
        return try {
            val units = appDataStore.tempUnit.first().toApiUnits()
            val language = appDataStore.language.first().toApiLang()
            val response = weatherService.getCurrentWeather(
                lat = lat,
                lon = long,
                apiKey = BuildConfig.WEATHER_API_KEY,
                units = units,
                lang = language
            )

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
            val units = appDataStore.tempUnit.first().toApiUnits()
            val language = appDataStore.language.first().toApiLang()
            val response = weatherService.getForecastWeather(
                lat = lat,
                lon = long,
                apiKey = BuildConfig.WEATHER_API_KEY,
                units = units,
                lang = language
            )

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