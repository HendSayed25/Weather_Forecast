package com.example.weatherforecast.presentation.screen.shared.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.designsystem.theme.Theme


@Composable
fun Loading(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier.fillMaxSize().background(Theme.color.background.screen)) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(24.dp)
        )
    }
}