package com.example.weatherforecast.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data class HomeRoute(val locationId : Int? = null) : Route
    @Serializable
    object MapRoute : Route
    @Serializable
    object FavoriteRoute : Route
}