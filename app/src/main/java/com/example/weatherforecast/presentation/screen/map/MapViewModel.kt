package com.example.weatherforecast.presentation.screen.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.local.datastore.LocationType
import com.example.weatherforecast.data.remote.model.Address
import com.example.weatherforecast.data.remote.model.Coordinate
import com.example.weatherforecast.data.repository.LocationRepository
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.presentation.screen.shared.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
    private val appDataStore: AppDataStore
) : ViewModel() {

    val currentLocation = appDataStore.location.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Coordinate(lat = 21.422525, long = 39.826181)
    )
    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    private val _locationDetails = MutableStateFlow(Address("", ""))

    fun onSaveButtonClick(lat: Double, long: Double) {
        viewModelScope.launch {
            if (appDataStore.locationType.first() == LocationType.MAP) appDataStore.setLocation(lat, long)
            else addToFav(
                lat = lat,
                long = long,
                cityName = _locationDetails.value.cityName,
                countryName = _locationDetails.value.countryName
            )
            _events.emit(UiEvent.NavigateBack)
        }
    }

    fun getAddress(lat: Double, long: Double, onResult: (Address) -> Unit) {
        viewModelScope.launch {
            locationRepository.getLocationDetails(lat, long).onSuccess {
                _locationDetails.value = it
                onResult(it)
            }.onFailure {
                _events.emit(UiEvent.ShowSnackbar(R.string.something_wrong))
            }
        }
    }

    suspend fun addToFav(lat: Double, long: Double, cityName: String, countryName: String) {
        try {
            weatherRepository.addWeatherToFav(
                lat = lat, long = long, cityName = cityName, countryName = countryName
            )
            _events.emit(UiEvent.ShowSnackbar(R.string.added))
        } catch (e: Exception) {
            _events.emit(UiEvent.ShowSnackbar(R.string.failed))
        }
    }
}