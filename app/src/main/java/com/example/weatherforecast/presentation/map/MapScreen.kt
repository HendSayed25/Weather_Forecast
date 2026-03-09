package com.example.weatherforecast.presentation.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.R
import com.example.weatherforecast.data.remote.model.Address
import com.example.weatherforecast.presentation.map.composable.CityField
import com.example.weatherforecast.presentation.map.composable.ConfirmButton
import com.example.weatherforecast.presentation.navigation.LocalNavController
import com.example.weatherforecast.presentation.shared.AppSnackbar
import com.example.weatherforecast.presentation.shared.UiEvent
import com.example.weatherforecast.presentation.utils.MapStyle
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.CameraState
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.map.OrnamentOptions
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.util.ClickResult
import org.maplibre.spatialk.geojson.Position
import kotlin.time.Duration.Companion.seconds

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    var pointerOffset by remember { mutableStateOf<DpOffset?>(null) }
    val coordinate by viewModel.currentLocation.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    var selectedPosition by remember { mutableStateOf<Position?>(null) }
    var selectedAddress by remember { mutableStateOf<Address?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val cameraState = rememberCameraState(firstPosition = CameraPosition())

    LaunchedEffect(coordinate) {
        cameraState.animateTo(
            finalPosition = cameraState.position.copy(
                target = Position(latitude = coordinate.lat, longitude = coordinate.long),
                zoom = 16.0
            ),
            duration = 5.seconds
        )
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is UiEvent.NavigateBack -> navController.navigateUp()
                is UiEvent.ShowSnackbar -> { snackbarHostState.showSnackbar(event.message) }
                else -> {}
            }
        }
    }

    MapScreenContent(
        pointerOffset = pointerOffset,
        snackbarHostState = snackbarHostState,
        onPointerOffsetChange = { offset, position ->
            pointerOffset = offset
            selectedPosition = position
            viewModel.getAddress(position.latitude, position.longitude) { address ->
                selectedAddress = address
            }
        },
        cameraState = cameraState,
        selectedAddress = selectedAddress,
        onClickConfirm = {
            selectedPosition?.let { pos ->
                viewModel.saveCityLocation(pos.latitude, pos.longitude)
            }
        }
    )
}

@Composable
private fun MapScreenContent(
    pointerOffset: DpOffset?,
    snackbarHostState: SnackbarHostState,
    onPointerOffsetChange: (DpOffset, Position) -> Unit,
    cameraState: CameraState,
    selectedAddress: Address?,
    onClickConfirm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        MaplibreMap(
            cameraState = cameraState,
            baseStyle = BaseStyle.Uri(MapStyle.OpenFreeMap.BRIGHT),
            options = MapOptions(
                ornamentOptions = OrnamentOptions.AllDisabled
            ),
            onMapClick = { coordinates, offset ->
                onPointerOffsetChange(offset, coordinates)
                ClickResult.Pass
            }
        )

        pointerOffset?.let { offset ->
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .offset(offset.x - 12.dp, offset.y - 24.dp),
                painter = painterResource(R.drawable.ic_location_dark),
                contentDescription = null
            )
        }

        CityField(
            modifier = modifier.align(Alignment.BottomCenter),
            selectedAddress = selectedAddress
        )

        ConfirmButton(
            modifier = modifier.align(Alignment.BottomCenter),
            pointerOffset = pointerOffset,
            onClickConfirm = onClickConfirm
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
            snackbar = { snackbarData ->
                AppSnackbar(
                    message = snackbarData.visuals.message
                )
            }
        )
    }
}