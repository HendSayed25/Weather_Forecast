package com.example.weatherforecast.presentation.screen.setting.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.data.local.datastore.WindSpeedUnit
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.designsystem.theme.WeatherForecastTheme

@Composable
fun MeasurementCard(
    selectedTemperature: TempUnit,
    onTemperatureSelect: (TempUnit) -> Unit,
    selectedWind: WindSpeedUnit,
    onWindSelect: (WindSpeedUnit) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(Theme.color.background.card)
    ) {

        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            Text(
                text = stringResource(R.string.temperature),
                style = Theme.textStyle.title.md,
                color = Theme.color.text.secondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TemperatureSegmentedControl(
                selected = selectedTemperature, onSelect = onTemperatureSelect
            )
        }

        HorizontalDivider(
            color = Theme.color.text.primary,
            modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 0.5.dp
        )

        Column(modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) {
            Text(
                text = stringResource(R.string.wind_speed),
                style = Theme.textStyle.title.md,
                color = Theme.color.text.secondary,
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
            )
            WindUnitRow(
                label = stringResource(R.string.meter_per_second),
                selected = selectedWind == WindSpeedUnit.METER_PER_SEC,
                onClick = { onWindSelect(WindSpeedUnit.METER_PER_SEC) })
            HorizontalDivider(
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 0.5.dp
            )
            WindUnitRow(
                label = stringResource(R.string.meter_per_hour),
                selected = selectedWind == WindSpeedUnit.MILE_PER_HOUR,
                onClick = { onWindSelect(WindSpeedUnit.MILE_PER_HOUR) })
        }
    }
}

@Composable
private fun TemperatureSegmentedControl(
    selected: TempUnit, onSelect: (TempUnit) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(9.dp))
            .padding(2.dp)
    ) {
       TempUnit.entries.forEach { unit ->
            val isSelected = selected == unit
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(7.dp))
                    .background(if (isSelected) Theme.color.text.primary.copy(alpha = 0.5f) else Color.Transparent)
                    .clickable { onSelect(unit) }
                    .padding(vertical = 7.dp),
                contentAlignment = Alignment.Center) {
                Text(
                    text = unit.symbol,
                    style = Theme.textStyle.title.md,
                    color = if (isSelected) Theme.color.background.card
                    else Theme.color.text.primary
                )
            }
        }
    }
}

@Composable
private fun WindUnitRow(
    label: String, selected: Boolean, onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = Theme.textStyle.title.md,
            color = Theme.color.text.primary,
            modifier = Modifier.weight(1f)
        )
        RadioDot(selected = selected)
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WeatherForecastTheme {
        MeasurementCard(TempUnit.FAHRENHEIT, {}, WindSpeedUnit.MILE_PER_HOUR, {})
    }
}