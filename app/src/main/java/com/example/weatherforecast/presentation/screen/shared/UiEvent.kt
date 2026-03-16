package com.example.weatherforecast.presentation.screen.shared

sealed class UiEvent {
    data class ShowSnackbar(val messageId: Int) : UiEvent()
    data object NavigateBack   : UiEvent()
    data object NavigateTo : UiEvent()
}