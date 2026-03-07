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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherforecast.designsystem.theme.WeatherForecastTheme
import com.example.weatherforecast.presentation.home.HomeScreen
import com.example.weatherforecast.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherForecastTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val homeViewModel  : HomeViewModel= hiltViewModel()

                    val permissionLauncher =
                        rememberLauncherForActivityResult(
                            ActivityResultContracts.RequestMultiplePermissions()
                        ) { result ->

                            val granted =
                                result[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                                        result[Manifest.permission.ACCESS_COARSE_LOCATION] == true

                            if(granted){
                                homeViewModel.getCurrentLocation()
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
                        viewModel = homeViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}