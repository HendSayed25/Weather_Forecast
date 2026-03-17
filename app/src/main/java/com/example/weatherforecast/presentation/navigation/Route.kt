package com.example.weatherforecast.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data class HomeRoute(val locationId : Int? = null) : Route
    @Serializable
    data class MapRoute(val isFromSetting : Boolean = false) : Route
    @Serializable
    object FavoriteRoute : Route
    @Serializable
    object AlertRoute : Route
    @Serializable
    object SettingRoute : Route
}