package com.example.weatherforecast.presentation.screen.shared

sealed class UiState <out T>{
    data class Success<T>(val data : T): UiState<T>()
    data class Error(val msg : String) : UiState<Nothing>()
    object Loading : UiState<Nothing>()
}