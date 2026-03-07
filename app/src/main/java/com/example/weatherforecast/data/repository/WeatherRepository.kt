package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.remote.datasource.WeatherRemoteDataSource
import com.example.weatherforecast.data.remote.response.ForecastResponse
import com.example.weatherforecast.data.remote.response.WeatherResponse
import javax.inject.Inject


class WeatherRepository @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) {
    suspend fun getCurrentWeather(lat: Double, long: Double): Result<WeatherResponse> {
        return weatherRemoteDataSource.getCurrentWeather(lat, long)
    }

    suspend fun getForecastWeather(lat: Double, long: Double): Result<ForecastResponse> {
        return weatherRemoteDataSource.getForecastWeather(lat, long)
    }
}