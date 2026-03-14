package com.example.weatherforecast.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.weatherforecast.R

data class BottomNavItem(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: Route
)

val bottomNavItems = listOf(
    BottomNavItem(R.string.home, Icons.Default.Home, Route.HomeRoute()),
    BottomNavItem(R.string.favorites, Icons.Default.Favorite, Route.FavoriteRoute),
    BottomNavItem(R.string.alerts, Icons.Default.Notifications, Route.AlertRoute),
    BottomNavItem(R.string.setting, Icons.Default.Settings, Route.SettingRoute),
)