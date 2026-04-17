package com.example.weatherforecast.presentation.screen.alert.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun CustomTextField(
    icon: ImageVector,
    value: String,
    onIconClick: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        readOnly = false,
        textStyle = TextStyle(
            fontSize = 12.sp,
            color = Theme.color.text.primary
        ),
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Theme.color.text.primary,
            unfocusedTextColor = Theme.color.text.primary,
            focusedBorderColor = Theme.color.background.screen,
            unfocusedBorderColor = Theme.color.background.screen
        ),
        trailingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Theme.color.background.screen,
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onIconClick() }
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    CustomTextField(
        modifier = Modifier,
        icon = Icons.Default.DateRange,
        value = "12/3/2026",
        onIconClick = {},
        onValueChange = { }
    )
}