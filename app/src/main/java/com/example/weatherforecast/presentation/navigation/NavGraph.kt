package com.example.weatherforecast.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.presentation.screen.alert.AlertScreen
import com.example.weatherforecast.presentation.screen.favorite.FavoriteScreen
import com.example.weatherforecast.presentation.screen.home.HomeScreen
import com.example.weatherforecast.presentation.screen.map.MapScreen
import com.example.weatherforecast.worker.Constants.ALERT_NAVIGATE_VALUE

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    locationGranted: Boolean,
    navigateTo: String? = null,
) {
    val navController = rememberNavController()

    LaunchedEffect(navigateTo) {
        if (navigateTo == ALERT_NAVIGATE_VALUE) {
            navController.navigate(Route.AlertRoute)
        }
    }

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = Route.AlertRoute,
            modifier = modifier
        ) {
            composable<Route.HomeRoute> {
                HomeScreen(locationGranted = locationGranted)
            }

            composable<Route.MapRoute> {
                MapScreen()
            }

            composable<Route.FavoriteRoute> {
                FavoriteScreen()
            }

            composable<Route.AlertRoute> {
                AlertScreen()
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavController> {
    error("NavController not provided")
}