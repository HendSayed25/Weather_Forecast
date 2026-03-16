package com.example.weatherforecast.presentation.screen.shared

sealed class UiState <out T>{
    data class Success<T>(val data : T): UiState<T>()
    data class Error(val msgId : Int) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}