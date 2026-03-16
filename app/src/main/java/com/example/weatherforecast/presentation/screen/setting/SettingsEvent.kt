package com.example.weatherforecast.presentation.screen.setting

sealed class SettingsEvent{
    data object NavigateToMapScreen : SettingsEvent()
    object RequestLocationPermission : SettingsEvent()
    data class ShowSnackbar(val messageId: Int) : SettingsEvent()
}