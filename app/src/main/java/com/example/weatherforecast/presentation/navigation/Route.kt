package com.example.weatherforecast.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    object HomeRoute : Route
}