package com.example.weatherforecast.presentation.screen.setting.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.data.local.datastore.Language
import com.example.weatherforecast.designsystem.theme.Theme
import com.example.weatherforecast.designsystem.theme.WeatherForecastTheme


@Composable
fun LanguageSection(
    language: Language, onLanguageSelect: (Language) -> Unit, modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { expanded = true })
            .clip(RoundedCornerShape(16.dp))
            .background(Theme.color.background.card)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Theme.color.text.tertiary), contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_language),
                contentDescription = "language icon",
                tint = Theme.color.background.card
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(R.string.language),
                style = Theme.textStyle.title.md,
                color = Theme.color.text.primary
            )
            Text(
                text = language.label,
                style = Theme.textStyle.title.sm,
                color = Theme.color.text.secondary
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Theme.color.text.primary,
            modifier = Modifier.size(30.dp)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(300.dp)
                .heightIn(max = 300.dp)
                .background(Theme.color.text.primary)
                .padding(16.dp)
        ) {
            Language.entries.forEach { lang ->
                DropdownMenuItem(
                    text = { Text(lang.label) },
                    onClick = {
                        onLanguageSelect(lang)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WeatherForecastTheme {
        LanguageSection(
            Language.ENGLISH, {})

    }
}