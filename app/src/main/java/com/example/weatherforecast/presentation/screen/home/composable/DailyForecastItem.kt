package com.example.weatherforecast.presentation.screen.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.utils.LanguageUtilUtils.getTempWithLabel

@Composable
fun DailyForecastItem(
    day: String,
    iconPainter: Painter,
    maxTemp: String,
    minTemp: String,
    isDay: Int,
    language: Language,
    tempUnit: TempUnit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Theme.color.background.card)
            .padding(vertical = 12.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = day,
            style = Theme.textStyle.title.sm,
            color = Theme.color.text.primary,
            textAlign = if (language == Language.ARABIC) TextAlign.End else TextAlign.Start,
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = iconPainter,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .size(40.dp)
                .weight(1f)
        )

        MinMaxDegree(
            maxTemp = "$maxTemp ${getTempWithLabel(language, tempUnit)}",
            minTemp = "$minTemp ${getTempWithLabel(language, tempUnit)}",
            isDay = isDay,
            showBackGround = false
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DailyForecastItemPreview() {
    DailyForecastItem(
        day = "Sunday",
        iconPainter = painterResource(R.drawable.ic_clouds),
        maxTemp = "8",
        minTemp = "22",
        isDay = 1,
        language = Language.ARABIC,
        tempUnit = TempUnit.CELSIUS
    )
}