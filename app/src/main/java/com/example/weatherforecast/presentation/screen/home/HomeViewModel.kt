package com.example.weatherforecast.presentation.screen.home

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.data.local.datastore.WindSpeedUnit
import com.example.weatherforecast.data.remote.model.Location
import com.example.weatherforecast.data.remote.response.ForecastResponse
import com.example.weatherforecast.data.remote.response.WeatherResponse
import com.example.weatherforecast.data.repository.LocationRepository
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.presentation.navigation.Route
import com.example.weatherforecast.presentation.screen.home.mapper.toCurrentWeatherUi
import com.example.weatherforecast.presentation.screen.home.mapper.toDaily
import com.example.weatherforecast.presentation.screen.home.mapper.toHourly
import com.example.weatherforecast.presentation.screen.home.mapper.toWeatherUiModel
import com.example.weatherforecast.presentation.screen.shared.UiState
import com.example.weatherforecast.presentation.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
    private val appDataStore: AppDataStore,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _locationUiState = MutableStateFlow<UiState<Location>>(UiState.Loading)
    val locationUiState = _locationUiState.asStateFlow()

    private val _weatherUiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val weatherState = _weatherUiState.asStateFlow()

    private val locationId: Int? = savedStateHandle.toRoute<Route.HomeRoute>().locationId

    val language = appDataStore.language.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Language.ENGLISH
        )

    val tempUnit = appDataStore.tempUnit.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TempUnit.CELSIUS
    )

    val windSpeedUnit = appDataStore.windSpeedUnit.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = WindSpeedUnit.METER_PER_SEC
    )


    fun getLocation() {
        if(!NetworkUtils.isInternetAvailable(context)){
            _weatherUiState.update { HomeUiState.NoInternet }
        }else{
            if (locationId == null) {
                getCurrentLocation()
            } else {
                getFavLocation()
            }
        }
    }

    fun getCurrentLocation() {
        viewModelScope.launch {
            locationRepository.getCurrentLocation().onSuccess {
                    _locationUiState.value = UiState.Success(it)
                    appDataStore.setLocation(it.lat, it.long)
                    getCurrentWeather()
                }.onFailure { errorState(R.string.get_location_error) }
        }
    }

    fun getCurrentWeather() {
        viewModelScope.launch {
            val locationState = _locationUiState.value
            if (locationState !is UiState.Success) return@launch
            val location = locationState.data

            weatherRepository.getCurrentWeather(
                location.lat, location.long
            ).onSuccess { weather -> getCurrentWeatherSuccess(weather) }
                .onFailure { errorState(R.string.something_wrong) }
        }
    }

    private fun getCurrentWeatherSuccess(weather: WeatherResponse) {
        val currentWeatherUi = weather.toCurrentWeatherUi()
        val weatherUiModel = weather.toWeatherUiModel()

        _weatherUiState.update { currentState ->

            if (currentState is HomeUiState.Success) {
                currentState.copy(
                    currentWeather = currentWeatherUi, weather = weatherUiModel
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
                    .onFailure { errorState(R.string.something_wrong) }
            }
        }
    }

    private fun getForecastWeatherSuccess(forecast: ForecastResponse) {
        val currentState = _weatherUiState.value

        if (currentState is HomeUiState.Success) {
            _weatherUiState.value = currentState.copy(
                dailyForecastItems = forecast.toDaily(),
                hourlyItems = forecast.toHourly(),
            )
        }
    }

    fun getFavLocation() {
        viewModelScope.launch {
            try {
                val id = locationId ?: return@launch
                val result = weatherRepository.getFavWeather(id = id)
                val location =
                    Location(result.lat, result.long, result.cityName, result.cityDescription)
                _locationUiState.value = UiState.Success(location)

                getCurrentWeather()
            } catch (e: Exception) {
                _locationUiState.value = UiState.Error(R.string.something_wrong)
            }
        }
    }

    private fun errorState(@StringRes messageId: Int) {
        _weatherUiState.value = HomeUiState.Error(messageId)
    }
}