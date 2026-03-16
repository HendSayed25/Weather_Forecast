package com.example.weatherforecast.presentation.screen.favorite

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.R
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.presentation.screen.favorite.mapper.toFavUiModel
import com.example.weatherforecast.presentation.screen.favorite.model.FavoriteItem
import com.example.weatherforecast.presentation.screen.shared.UiEvent
import com.example.weatherforecast.presentation.screen.shared.UiState
import com.example.weatherforecast.presentation.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<FavoriteItem>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>()
    val events = _events.asSharedFlow()

    init {
        getFavWeathers()
    }

    fun onAddFavorite() {
        viewModelScope.launch {
            if (!NetworkUtils.isInternetAvailable(context)) {
                _events.emit(UiEvent.ShowSnackbar(R.string.no_internet))
            } else {
                _events.emit(UiEvent.NavigateTo)
            }
        }
    }

    fun getFavWeathers() {
        viewModelScope.launch {
            weatherRepository.getAllFavWeathers()
                .catch { _events.emit(UiEvent.ShowSnackbar(R.string.something_wrong)) }
                .collect { weathers -> _uiState.value = UiState.Success(weathers.map { it.toFavUiModel() }) }
        }
    }

    fun deleteFromFavorite(weatherId: Int) {
        viewModelScope.launch {
            try {
                weatherRepository.deleteWeatherFromFav(weatherId)
                _events.emit(UiEvent.ShowSnackbar(R.string.deleted_success))
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowSnackbar(R.string.something_wrong))
            }
        }
    }
}