package com.example.weatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weatherforecast.data.local.datastore.AppDataStore
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.designsystem.theme.WeatherForecastTheme
import com.example.weatherforecast.presentation.navigation.NavGraph
import com.example.weatherforecast.presentation.utils.LanguageUtilUtils.setAppLocale
import com.example.weatherforecast.presentation.worker.Constants.ALERT_NAVIGATE_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val navigateTo = mutableStateOf<String?>(null)
    @Inject
    lateinit var appDataStore: AppDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        navigateTo.value = intent.getStringExtra(ALERT_NAVIGATE_KEY)
        setContent {
            val language by appDataStore.language.collectAsStateWithLifecycle(Language.ENGLISH)
            this.setAppLocale(language.code)

            WeatherForecastTheme {
                NavGraph(
                    modifier = Modifier,
                    navigateTo = navigateTo.value,
                    language = language
                )
            }
        }
    }
}