package com.example.weatherforecast.presentation.screen.map.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.data.remote.model.Address
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun CityField(
    selectedAddress: Address?,
    modifier: Modifier = Modifier
) {

    AnimatedVisibility(
        visible = selectedAddress != null,
        modifier = modifier.padding(bottom = 70.dp),
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
    ) {
        selectedAddress?.let { address ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.75f),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Theme.color.background.card
                ),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = address.cityName,
                        style = Theme.textStyle.title.md,
                        color = Theme.color.text.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = address.countryName,
                        style = Theme.textStyle.title.md,
                        color = Theme.color.text.secondary
                    )
                }
            }
        }
    }
}