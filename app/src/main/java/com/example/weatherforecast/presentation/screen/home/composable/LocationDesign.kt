package com.example.weatherforecast.presentation.screen.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.utils.LanguageUtilUtils.formatDateByLanguage

@Composable
fun LocationDesign(
    cityName: String,
    isDay: Int,
    date: String,
    language: Language,
    modifier: Modifier = Modifier
) {
    val layoutDirection = if (language == Language.ARABIC) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.today),
                color = Theme.color.text.primary,
                style = Theme.textStyle.title.xl,
                modifier = Modifier.padding(start = 10.dp, top = 20.dp)
            )

            Text(
                text = formatDateByLanguage(date, language),
                color = Theme.color.text.primary,
                style = Theme.textStyle.title.sm,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(
                        if (isDay == 1) R.drawable.ic_location_light
                        else R.drawable.ic_location_dark
                    ),
                    contentDescription = "location icon",
                    modifier = Modifier.padding(top = 4.dp)
                )

                Box(Modifier.padding(start = 4.dp)) {
                    Text(
                        text = cityName,
                        style = Theme.textStyle.title.lg,
                        color = Theme.color.text.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LocationDesignPreview() {
    LocationDesign(
        cityName = "Cairo",
        isDay = 1,
        date = "Friday, 12 Feb",
        language = Language.ARABIC
    )
}