package com.example.weatherforecast.presentation.screen.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.presentation.screen.home.model.WeatherState

@Composable
fun WeatherStateContent(
    weatherStates :List<WeatherState>,
    modifier: Modifier = Modifier,
    languageCode : String
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp).padding(top=24.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            WeatherStateCard(Modifier.weight(1f),weatherStates[0],languageCode)
            WeatherStateCard(Modifier.weight(1f),weatherStates[1],languageCode)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            WeatherStateCard(Modifier.weight(1f),weatherStates[2],languageCode)
            WeatherStateCard(Modifier.weight(1f),weatherStates[3],languageCode)
        }
    }
}