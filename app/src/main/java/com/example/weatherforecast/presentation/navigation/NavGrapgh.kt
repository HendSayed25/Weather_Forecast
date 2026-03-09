package com.example.weatherforecast.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.presentation.map.MapScreen
import com.example.weatherforecast.presentation.home.HomeScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    locationGranted: Boolean
) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = Route.HomeRoute,
            modifier = modifier
        ) {
            composable<Route.HomeRoute> {
                HomeScreen(locationGranted = locationGranted)
            }

            composable<Route.MapScreen> {
                MapScreen()
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavController> {
    error("NavController not provided")
}