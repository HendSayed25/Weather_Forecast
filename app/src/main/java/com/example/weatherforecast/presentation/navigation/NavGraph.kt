package com.example.weatherforecast.presentation.navigation

import BottomNavBar
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.presentation.screen.alert.AlertScreen
import com.example.weatherforecast.presentation.screen.favorite.FavoriteScreen
import com.example.weatherforecast.presentation.screen.home.HomeScreen
import com.example.weatherforecast.presentation.screen.map.MapScreen
import com.example.weatherforecast.presentation.screen.setting.SettingScreen
import com.example.weatherforecast.worker.Constants.ALERT_NAVIGATE_VALUE

@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    navigateTo: String? = null,
    language: Language = Language.ENGLISH,
) {
    val navController = rememberNavController()
    val layoutDirection = if (language == Language.ARABIC) LayoutDirection.Rtl else LayoutDirection.Ltr

    LaunchedEffect(navigateTo) {
        if (navigateTo == ALERT_NAVIGATE_VALUE) {
            navController.navigate(Route.AlertRoute)
        }
    }

    CompositionLocalProvider(
        LocalNavController provides navController, LocalLayoutDirection provides layoutDirection
    ) {
        Scaffold(
            bottomBar = { BottomNavBar(navController) }) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Route.HomeRoute(),
                modifier = modifier.padding(paddingValues)
            ) {
                composable<Route.HomeRoute> {
                    HomeScreen()
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

                composable<Route.SettingRoute> {
                    SettingScreen()
                }
            }
        }
    }
}

val LocalNavController = compositionLocalOf<NavController> {
    error("NavController not provided")
}