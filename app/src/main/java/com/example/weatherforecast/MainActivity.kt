package com.example.weatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.example.weatherforecast.designsystem.theme.WeatherForecastTheme
import com.example.weatherforecast.presentation.navigation.NavGraph
import com.example.weatherforecast.worker.Constants.ALERT_NAVIGATE_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val navigateTo = mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        navigateTo.value = intent.getStringExtra(ALERT_NAVIGATE_KEY)

        setContent {
            WeatherForecastTheme {
                NavGraph(
                    modifier = Modifier,
                    navigateTo = navigateTo.value
                )
            }
        }
    }
}