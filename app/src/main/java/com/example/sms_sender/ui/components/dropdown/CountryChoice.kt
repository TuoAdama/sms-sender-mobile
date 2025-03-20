package com.example.sms_sender.ui.components.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.sms_sender.R
import com.example.sms_sender.service.CountryCodeHandler


@Composable
@Preview
fun CountryChoice(modifier: Modifier = Modifier, onChange: (choice: String) -> Unit  = {message -> println(message) }) {
    SelectComponent(
        title = stringResource(R.string.country),
        data = CountryCodeHandler.countriesMap,
    ) {}
}