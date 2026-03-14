package com.example.weatherforecast.presentation.screen.alert.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.weatherforecast.R
import com.example.weatherforecast.designsystem.theme.Theme

@Composable
fun ConditionDropdown(
    selectedCondition: String,
    onConditionSelected: (String) -> Unit
) {
    val conditions = getConditions()
    var dropdownExpanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = selectedCondition,
            onValueChange = { onConditionSelected(it) },
            readOnly = true,
            enabled = false,
            textStyle = TextStyle(
                color = Theme.color.text.primary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { dropdownExpanded = true }
                .border(color = Theme.color.background.screen, width = 0.5.dp),
            trailingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        )

        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
            modifier = Modifier
                .width(300.dp)
                .heightIn(max = 300.dp)
                .background(Theme.color.background.card)
                .padding(16.dp)
        ) {
            conditions.forEach { reason ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = reason, style = Theme.textStyle.title.md,
                            color = Theme.color.text.primary,
                        )
                    },
                    onClick = {
                        onConditionSelected(reason)
                        dropdownExpanded = false
                    }
                )
            }
        }
    }
}

@Composable
private fun getConditions(): List<String> {
    return listOf(
        stringResource(R.string.condition_select_reason),
        stringResource(R.string.condition_clouds),
        stringResource(R.string.condition_scattered_clouds),
        stringResource(R.string.condition_clear),
        stringResource(R.string.condition_rain),
        stringResource(R.string.condition_snow),
        stringResource(R.string.condition_sand)
    )
}