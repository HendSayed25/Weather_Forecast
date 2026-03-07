package com.example.weatherforecast.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.presentation.home.HomeScreen

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    locationGranted: Boolean
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HomeRoute,
        modifier = modifier
    ) {
        composable<Route.HomeRoute> {
            HomeScreen(modifier = modifier, locationGranted = locationGranted)
        }
    }
}