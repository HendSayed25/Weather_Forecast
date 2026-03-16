package com.example.weatherforecast.presentation.screen.alert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.entity.Alert
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.presentation.screen.alert.mapper.toEntity
import com.example.weatherforecast.presentation.screen.alert.mapper.toUiModel
import com.example.weatherforecast.presentation.screen.alert.model.AlertModel
import com.example.weatherforecast.presentation.screen.shared.UiEvent
import com.example.weatherforecast.presentation.screen.shared.UiState
import com.example.weatherforecast.presentation.utils.TimeUtils
import com.example.weatherforecast.presentation.worker.AlertScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(
    private val weatherRepo: WeatherRepository,
    private val scheduler: AlertScheduler,
    appDataStore: AppDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<AlertModel>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    val language = appDataStore.language.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Language.ENGLISH
    )

    init {
        getAllAlerts()
    }

    fun getAllAlerts() {
        viewModelScope.launch {
            weatherRepo.getAllAlerts()
                .catch { _events.emit(UiEvent.ShowSnackbar(R.string.something_wrong)) }
                .collect { alerts ->
                    _uiState.value = UiState.Success(alerts.map { it.toUiModel(language.value.code) })
                }
        }
    }

    fun addAlert(
        title: String,
        time: String,
        date: String,
        condition: String,
        type: String,
        isEnable: Boolean
    ) {
        viewModelScope.launch {
            try {
                val timeMillis = TimeUtils.convertToMillis(date, time, language.value.code)
                val alert = Alert(
                    title = title,
                    time = timeMillis,
                    date = timeMillis,
                    condition = condition,
                    type = type,
                    isEnable = isEnable
                )
                val id = weatherRepo.addAlert(
                    title = title,
                    time = timeMillis,
                    date = timeMillis,
                    condition = condition,
                    type = type,
                    isEnable = isEnable
                )
                scheduler.scheduleAlert(alert.copy(id = id.toInt()))
                _events.emit(UiEvent.ShowSnackbar(R.string.add_success))
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowSnackbar(R.string.something_wrong))
            }
        }
    }

    fun updateAlert(alert: AlertModel) {
        viewModelScope.launch {
            try {
                weatherRepo.updateAlert(alert.toEntity())
                cancelAlert(alert.id)
                scheduleAlert(alert)
                _events.emit(UiEvent.ShowSnackbar(R.string.update_success))
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowSnackbar(R.string.something_wrong))
            }
        }
    }

    fun deleteAlert(alert: AlertModel) {
        viewModelScope.launch {
            try {
                weatherRepo.deleteAlert(alert.toEntity())
                _events.emit(UiEvent.ShowSnackbar(R.string.deleted_success))
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowSnackbar(R.string.something_wrong))
            }
        }
    }

    fun scheduleAlert(alert: AlertModel) {
        viewModelScope.launch {
            scheduler.scheduleAlert(alert.toEntity())
        }
    }

    fun cancelAlert(alertId: Int) {
        viewModelScope.launch {
            scheduler.cancelAlert(alertId)
        }
    }
}