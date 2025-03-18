package com.example.sms_sender.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import com.example.sms_sender.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleSelect(
    modifier: Modifier = Modifier,
    value: Int = 10_000,
    onSelect: (value: Int) -> Unit = {}
){

    val choices = mapOf(
        "10 secs" to 10000,
        "30 secs" to 30000,
        "1 minute" to 60000,
        "2 minutes" to 120000,
        "5 minutes" to 300000
    )


    val key  = choices.entries.firstOrNull { it.value == value }?.key ?: choices.keys.first()

    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(key) } // Première valeur par défaut

    Box(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                label = { Text(stringResource(R.string.retreive_frequency)) },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.dropdown_icon))
                }
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                choices.forEach { (label, value) ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            selectedText = label
                            onSelect(value)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}