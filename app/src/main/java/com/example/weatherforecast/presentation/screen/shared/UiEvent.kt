package com.example.weatherforecast.presentation.screen.shared

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    data class Navigate(val route: String)       : UiEvent()
    data object NavigateBack                     : UiEvent()
    data object ShowLoading                      : UiEvent()
    data object HideLoading                      : UiEvent()
}