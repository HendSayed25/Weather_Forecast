package com.example.weatherforecast.presentation.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.presentation.home.model.WeatherState


@Composable
fun WeatherStateCard(
    modifier: Modifier = Modifier,
    weatherStateData: WeatherState,
) {

    Box(
        modifier = modifier.background(
            Theme.color.background.screen,
            shape = RoundedCornerShape(24.dp)
        ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Image(
                painter = painterResource(weatherStateData.iconId),
                contentDescription = "icon for weather state",
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = weatherStateData.value,
                style = Theme.textStyle.label.md.semiBold,
                color = Theme.color.text.primary,
                modifier = Modifier.padding(bottom = 2.dp)
            )

            Text(
                text = weatherStateData.state,
                fontSize = 14.sp,
                style = Theme.textStyle.label.md.regular,
                color = Theme.color.text.primary,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WeatherStateCard(
        weatherStateData = WeatherState(
            iconId = R.drawable.ic_uv_light,
            value = "10 km",
            state = "Visibility"
        )
    )
}