package com.example.sms_sender.ui.components.dropdown

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
fun <T> SelectComponent(
    modifier: Modifier = Modifier,
    title: String,
    data: Map<String, T>,
    defaultValue: String? = null,
    onChange: (value: T) -> Unit,
) {

    var valueSelected by remember {
        mutableStateOf( defaultValue ?: data.keys.first())
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Box(modifier = modifier) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            OutlinedTextField(
                value = valueSelected,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                label = { Text(title) },
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.dropdown_icon))
                }
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                data.forEach { (label, value) ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            valueSelected = label
                            onChange(value)
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
}