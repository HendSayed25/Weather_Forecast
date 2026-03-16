package com.example.weatherforecast.presentation.screen.setting.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.LocationType
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.designsystem.theme.WeatherForecastTheme

@Composable
fun LocationCard(
    selectedType: LocationType, onSelect: (LocationType) -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(
            containerColor = Theme.color.background.card
        ), elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        GpsLocationRow(
            selected = selectedType == LocationType.GPS, onClick = { onSelect(LocationType.GPS) })
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = Theme.color.text.primary,
            thickness = 0.5.dp
        )
        NavigationRow(
            iconBackground = Color(0xFFEEEEEE),
            title = R.string.set_manually_location,
            selected = selectedType == LocationType.MAP,
            onClick = { onSelect(LocationType.MAP) })
    }
}

@Composable
private fun GpsLocationRow(selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Theme.color.text.tertiary), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_location),
                contentDescription = "location icon",
                tint = Theme.color.background.card
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.use_gps_location),
                style = Theme.textStyle.title.md,
                color = Theme.color.text.primary
            )
            Text(
                text = stringResource(R.string.detect_current_city),
                style = Theme.textStyle.title.sm,
                color = Theme.color.text.secondary
            )
        }

        RadioDot(selected)

    }
}

@Composable
private fun NavigationRow(
    iconBackground: Color,
    selected: Boolean,
    onClick: () -> Unit,
    @StringRes title: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBackground), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_map),
                contentDescription = "map icon",
                tint = Theme.color.background.card,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = stringResource(title),
            style = Theme.textStyle.title.md,
            color = Theme.color.text.primary,
            modifier = Modifier.weight(1f)
        )
        RadioDot(selected)
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    WeatherForecastTheme {
        LocationCard(
            selectedType = LocationType.GPS, onSelect = {})
    }
}