package com.example.weatherforecast.presentation.screen.setting

import android.app.Activity
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.LocationType
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.data.local.datastore.WindSpeedUnit
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.navigation.LocalNavController
import com.example.weatherforecast.presentation.navigation.Route
import com.example.weatherforecast.presentation.screen.setting.composables.LanguageSection
import com.example.weatherforecast.presentation.screen.setting.composables.LocationCard
import com.example.weatherforecast.presentation.screen.setting.composables.MeasurementCard
import com.example.weatherforecast.presentation.screen.shared.composable.AppSnackbar
import requestLocationPermissionWithSettingsRedirect

@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val activity = context as? Activity ?: return
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val requestLocationPermission = requestLocationPermissionWithSettingsRedirect(
        onGranted = viewModel::getCurrentLocation, activity = activity
    )

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is SettingsEvent.RequestLocationPermission -> {
                    requestLocationPermission()
                }

                is SettingsEvent.NavigateToMapScreen -> {
                    navController.navigate(Route.MapRoute(true))
                }

                is SettingsEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(context.getString(event.messageId))
                }
            }
        }
    }

    SettingScreenContent(
        locationType = state.locationType,
        onSelectLocationType = viewModel::changeLocationType,
        onSelectTemperatureUnit = viewModel::changeTemperatureUnit,
        onSelectWindUnit = viewModel::changeWindUnit,
        onSelectLanguage = viewModel::changeLanguage,
        tempUnit = state.temperatureUnit,
        windSpeedUnit = state.windUnit,
        language = state.language,
        snackbarHostState = snackbarHostState
    )

}

@Composable
private fun SettingScreenContent(
    locationType: LocationType,
    onSelectLocationType: (LocationType) -> Unit,
    onSelectTemperatureUnit: (TempUnit) -> Unit,
    tempUnit: TempUnit,
    onSelectWindUnit: (WindSpeedUnit) -> Unit,
    windSpeedUnit: WindSpeedUnit,
    onSelectLanguage: (Language) -> Unit,
    language: Language,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .background(Theme.color.background.screen)
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.setting),
                style = Theme.textStyle.title.lg,
                color = Theme.color.text.primary,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            SectionHeader(
                text = R.string.location_service
            )

            LocationCard(
                selectedType = locationType,
                onSelect = onSelectLocationType,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            SectionHeader(
                text = R.string.measurement_units
            )

            MeasurementCard(
                selectedTemperature = tempUnit,
                onTemperatureSelect = onSelectTemperatureUnit,
                selectedWind = windSpeedUnit,
                onWindSelect = onSelectWindUnit,
            )

            SectionHeader(
                text = R.string.app_pref, modifier = Modifier.padding(top = 20.dp)
            )

            LanguageSection(
                language = language,
                onLanguageSelect = onSelectLanguage,
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            snackbar = { snackbarData ->
                AppSnackbar(
                    message = snackbarData.visuals.message
                )
            })
    }
}

@Composable
private fun SectionHeader(
    @StringRes text: Int, modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(text),
        style = Theme.textStyle.title.md,
        color = Theme.color.text.secondary,
        modifier = modifier.padding(vertical = 12.dp)
    )
}