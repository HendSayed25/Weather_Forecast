package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.local.datasource.WeatherLocalDataSource
import com.example.weatherforecast.data.local.entity.Weather
import com.example.weatherforecast.data.remote.datasource.WeatherRemoteDataSource
import com.example.weatherforecast.data.remote.response.ForecastResponse
import com.example.weatherforecast.data.remote.response.WeatherResponse
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow


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

    suspend fun addWeatherToFav(lat: Double, long: Double,cityName : String , countryName : String){
        weatherLocalDataSource.addWeather(Weather(lat = lat, long = long, cityName = cityName, cityDescription = countryName))
    }

    suspend fun deleteWeatherFromFav(weather: Weather){
        weatherLocalDataSource.deleteWeather(weather)
    }

    fun getAllFavWeathers() : Flow<List<Weather>>{
        return weatherLocalDataSource.getAllWeathers()
    }
}