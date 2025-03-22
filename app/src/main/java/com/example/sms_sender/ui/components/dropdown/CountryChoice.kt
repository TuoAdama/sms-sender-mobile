package com.example.sms_sender.ui.components.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sms_sender.R


@Composable
@Preview
fun CountryChoice(modifier: Modifier = Modifier, onChange: (choice: String) -> Unit  = {message -> println(message) }) {

    val countries = mapOf(
        "France" to 33,
    )

    SelectComponent(
        title = stringResource(R.string.country),
        data = countries,
    ) {}
}