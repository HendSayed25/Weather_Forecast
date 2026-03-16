package com.example.weatherforecast.presentation.screen.home.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.data.local.datastore.TempUnit
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.screen.home.model.DailyForecastItem
import com.example.weatherforecast.presentation.utils.LanguageUtilUtils.formatNumberByLocale
import com.example.weatherforecast.presentation.utils.LanguageUtilUtils.getDayNameByLocale

@Composable
fun Next5DaysForecastCard(
    dailyForecastItems: List<DailyForecastItem>,
    isDay: Int,
    language: Language,
    tempUnit: TempUnit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Theme.color.background.card),
        border = BorderStroke(width = 0.5.dp, color = MaterialTheme.colorScheme.onBackground),
    ) {

        Column(modifier = Modifier.fillMaxWidth()) {
            for (i in 0..<dailyForecastItems.size) {
                DailyForecastItem(
                    day = getDayNameByLocale(dailyForecastItems[i].day, language.code),
                    iconPainter = painterResource(dailyForecastItems[i].imageId),
                    maxTemp = formatNumberByLocale(
                        number = dailyForecastItems[i].maxTemp.toInt(), languageCode = language.code
                    ),
                    minTemp = formatNumberByLocale(
                        number = dailyForecastItems[i].minTemp.toInt(), languageCode = language.code
                    ),
                    isDay = isDay,
                    language = language,
                    tempUnit = tempUnit
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Next5DaysForecastCardPreview() {
    fun getFakeDailyForecast(): List<DailyForecastItem> {
        return listOf(
            DailyForecastItem(
                day = "Mon", minTemp = 18.0, maxTemp = 26.0, imageId = R.drawable.ic_clouds
            ), DailyForecastItem(
                day = "Tue", minTemp = 17.0, maxTemp = 24.0, imageId = R.drawable.ic_rain
            ), DailyForecastItem(
                day = "Wed", minTemp = 16.0, maxTemp = 23.0, imageId = R.drawable.ic_clear
            ), DailyForecastItem(
                day = "Thu", minTemp = 19.0, maxTemp = 27.0, imageId = R.drawable.ic_drizzle
            ), DailyForecastItem(
                day = "Fri", minTemp = 20.0, maxTemp = 29.0, imageId = R.drawable.ic_mist
            ), DailyForecastItem(
                day = "Sat", minTemp = 21.0, maxTemp = 30.0, imageId = R.drawable.ic_mist
            ), DailyForecastItem(
                day = "Sun", minTemp = 22.0, maxTemp = 31.0, imageId = R.drawable.ic_rain
            )
        )
    }
    Next5DaysForecastCard(
        getFakeDailyForecast(), 1, Language.ENGLISH, TempUnit.CELSIUS
    )
}