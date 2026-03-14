package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.local.datasource.WeatherLocalDataSource
import com.example.weatherforecast.data.local.entity.Alert
import com.example.weatherforecast.data.local.entity.Weather
import com.example.weatherforecast.data.remote.datasource.WeatherRemoteDataSource
import com.example.weatherforecast.data.remote.response.ForecastResponse
import com.example.weatherforecast.data.remote.response.WeatherResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class WeatherRepository @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
) {
    suspend fun getCurrentWeather(lat: Double, long: Double): Result<WeatherResponse> {
        return weatherRemoteDataSource.getCurrentWeather(lat, long)
    }

    suspend fun getForecastWeather(lat: Double, long: Double): Result<ForecastResponse> {
        return weatherRemoteDataSource.getForecastWeather(lat, long)
    }

    suspend fun addWeatherToFav(lat: Double, long: Double, cityName: String, countryName: String) {
        weatherLocalDataSource.addWeather(
            Weather(
                lat = lat,
                long = long,
                cityName = cityName,
                cityDescription = countryName
            )
        )
    }

    suspend fun deleteWeatherFromFav(weatherId: Int) {
        weatherLocalDataSource.deleteWeather(weatherId)
    }

    fun getAllFavWeathers(): Flow<List<Weather>> {
        return weatherLocalDataSource.getAllWeathers()
    }

    suspend fun getFavWeather(id: Int): Weather {
        return weatherLocalDataSource.getFavWeather(id)
    }

    suspend fun addAlert(
        title: String,
        time: Long,
        date: Long,
        condition: String,
        type: String,
        isEnable: Boolean
    ): Long {
        return weatherLocalDataSource.addAlert(
            Alert(
                title = title,
                time = time,
                date = date,
                condition = condition,
                type = type,
                isEnable = isEnable
            )
        )
    }

    suspend fun deleteAlert(alert: Alert) {
        weatherLocalDataSource.deleteAlert(alert)
    }

    fun getAllAlerts(): Flow<List<Alert>> {
        return weatherLocalDataSource.getAllAlerts()
    }

    suspend fun updateAlert(alert: Alert) {
        weatherLocalDataSource.updateAlert(alert)
    }
}