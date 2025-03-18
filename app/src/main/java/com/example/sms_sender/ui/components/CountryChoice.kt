package com.example.sms_sender.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sms_sender.service.CountryCodeHandler


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CountryChoice(modifier: Modifier = Modifier, onChange: (choice: String) -> Unit  = {message -> println(message) }) {

    val countries: List<String> = CountryCodeHandler.getCountries();
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }

    Box(Modifier.fillMaxWidth()){
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = !isExpanded
            }
        ) {
            OutlinedTextField(
                value = countries[selectedIndex],
                onValueChange = {},
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded =  isExpanded)
                }
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {isExpanded = false},
                modifier = Modifier.fillMaxWidth(),
            ) {
                countries.forEachIndexed {index, text ->
                    DropdownMenuItem(
                        text = {
                            Text(text)
                        },
                        onClick = {
                            selectedIndex = index
                            isExpanded = false
                            onChange(countries[index])
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}