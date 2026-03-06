package com.example.weatherforecast

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherforecast.data.remote.datasource.AddressRemoteDataSource
import com.example.weatherforecast.data.remote.datasource.CoordinateRemoteDataSource
import com.example.weatherforecast.data.remote.datasource.WeatherRemoteDataSource
import com.example.weatherforecast.data.repository.LocationRepository
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.designsystem.theme.WeatherForecastTheme
import com.example.weatherforecast.presentation.home.HomeScreen
import com.example.weatherforecast.presentation.home.HomeViewFactory
import com.example.weatherforecast.presentation.home.HomeViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherForecastTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val factory = HomeViewFactory(application, LocationRepository(
                        CoordinateRemoteDataSource(),AddressRemoteDataSource()),
                        WeatherRepository(WeatherRemoteDataSource()))

                    val view = viewModel<HomeViewModel>(factory = factory)

                    val permissionLauncher =
                        rememberLauncherForActivityResult(
                            ActivityResultContracts.RequestMultiplePermissions()
                        ) { result ->

                            val granted =
                                result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                                        result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

                            if(granted){
                                view.getCurrentLocation()
                            }
                        }

                    LaunchedEffect(Unit){
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }

                    HomeScreen(
                        viewModel = view,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}