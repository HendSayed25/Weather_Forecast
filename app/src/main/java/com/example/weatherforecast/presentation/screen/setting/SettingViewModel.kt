package com.example.weatherforecast.presentation.screen.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.LocationType
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.data.local.datastore.WindSpeedUnit
import com.example.weatherforecast.data.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val appDataStore: AppDataStore,
    private val locationRepository: LocationRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<SettingsEvent>()
    val events = _events.asSharedFlow()

    val uiState = combine(
        appDataStore.locationType,
        appDataStore.tempUnit,
        appDataStore.windSpeedUnit,
        appDataStore.language
    ) { location, temp, wind, lang ->
        SettingsState(location, temp, wind, lang)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsState()
    )

    fun getCurrentLocation() {
        viewModelScope.launch {
            locationRepository.getCurrentLocation(true)
                .onSuccess { appDataStore.setLocation(it.lat, it.long) }
                .onFailure { _events.emit(SettingsEvent.ShowSnackbar(R.string.get_location_error)) }
        }
    }

    fun changeLocationType(type: LocationType) {
        viewModelScope.launch {
            appDataStore.setLocationType(type)
            when (type) {
                LocationType.GPS -> _events.emit(SettingsEvent.RequestLocationPermission)
                LocationType.MAP -> _events.emit(SettingsEvent.NavigateToMapScreen)
            }
        }
    }

    fun changeTemperatureUnit(unit: TempUnit) {
        viewModelScope.launch {
            appDataStore.setTempUnit(unit)
        }
    }

    fun changeWindUnit(unit: WindSpeedUnit) {
        viewModelScope.launch {
            appDataStore.setWindSpeedUnit(unit)
        }
    }

    fun changeLanguage(language: Language) {
        viewModelScope.launch {
            appDataStore.setLanguage(language)
        }
    }
}