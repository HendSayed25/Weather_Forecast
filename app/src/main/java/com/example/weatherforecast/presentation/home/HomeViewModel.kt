package com.example.weatherforecast.presentation.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.data.remote.model.Location
import com.example.weatherforecast.data.remote.response.ForecastResponse
import com.example.weatherforecast.data.remote.response.WeatherResponse
import com.example.weatherforecast.data.repository.LocationRepository
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.presentation.home.mapper.toCurrentWeatherUi
import com.example.weatherforecast.presentation.home.mapper.toDaily
import com.example.weatherforecast.presentation.home.mapper.toHourly
import com.example.weatherforecast.presentation.home.mapper.toWeatherUiModel
import com.example.weatherforecast.presentation.shared.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onSuccess

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val context: Application,
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _locationUiState = MutableStateFlow<UiState<Location>>(UiState.Loading)
    val locationUiState = _locationUiState.asStateFlow()

    private val _weatherUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val weatherState = _weatherUiState.asStateFlow()

    fun getCurrentLocation() {
        viewModelScope.launch {
            locationRepository.getCurrentLocation(context)
                .onSuccess {
                    _locationUiState.value = UiState.Success(it)
                    Log.d("Location","${it.lat }+ ${it.long}")
                    getCurrentWeather()
                }
                .onFailure { errorState("Failed to get Your Location , Try Again") }
        }
    }

    fun getCurrentWeather() {
        viewModelScope.launch {
            val locationState = _locationUiState.value
            if (locationState !is UiState.Success) return@launch
            val location = locationState.data

            weatherRepository.getCurrentWeather(
                location.lat,
                location.long
            ).onSuccess { weather -> getCurrentWeatherSuccess(weather) }
                .onFailure { errorState("Something went wrong") }
        }
    }

    private fun getCurrentWeatherSuccess(weather: WeatherResponse) {
        val currentWeatherUi = weather.toCurrentWeatherUi()
        val weatherUiModel = weather.toWeatherUiModel()

        _weatherUiState.update { currentState ->

            if (currentState is HomeUiState.Success) {
                currentState.copy(
                    currentWeather = currentWeatherUi,
                    weather = weatherUiModel
                )
            } else {
                HomeUiState.Success(
                    currentWeather = currentWeatherUi,
                    weather = weatherUiModel,
                    dailyForecastItems = emptyList(),
                    hourlyItems = emptyList()
                )
            }
        }
        getForecastWeather()
    }

    fun getForecastWeather() {
        viewModelScope.launch {
            val locationState = _locationUiState.value

            if (locationState is UiState.Success) {
                val location = locationState.data
                val res = weatherRepository.getForecastWeather(location.lat, location.long)

                res.onSuccess { forecast -> getForecastWeatherSuccess(forecast) }
                    .onFailure { errorState("Something went wrong") }
            }
        }
    }

    private fun getForecastWeatherSuccess(forecast : ForecastResponse){
        val currentState = _weatherUiState.value

        if (currentState is HomeUiState.Success) {
            _weatherUiState.value = currentState.copy(
                dailyForecastItems = forecast.toDaily(),
                hourlyItems = forecast.toHourly(),
            )
        }
    }

    private fun errorState(message : String){
        _weatherUiState.value = HomeUiState.Error(message)
    }
}