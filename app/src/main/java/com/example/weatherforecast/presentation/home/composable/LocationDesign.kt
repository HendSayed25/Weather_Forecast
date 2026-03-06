package com.example.weatherforecast.presentation.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun LocationDesign(
    cityName: String,
    isDay: Int,
    date: String,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.today),
            color = Theme.color.text.primary,
            style = Theme.textStyle.title.xl,
            modifier = Modifier.padding(start = 10.dp, top = 20.dp)
        )

        Text(
            text = date,
            color = Theme.color.text.primary,
            style = Theme.textStyle.title.sm,
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
        ) {

            Image(
                painter = painterResource(if (isDay == 1) R.drawable.ic_location_dark else R.drawable.ic_location_light),
                contentDescription = "location icon",
                modifier = Modifier.padding(top = 4.dp)
            )

            Box(Modifier.padding(start = 4.dp)) {
                Text(
                    text = cityName,
                    style = Theme.textStyle.title.xl,
                    color = Theme.color.text.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationDesignPreview() {
    LocationDesign(
        "Cairo",
        1,
        "Friday, 12 Feb"
    )
}