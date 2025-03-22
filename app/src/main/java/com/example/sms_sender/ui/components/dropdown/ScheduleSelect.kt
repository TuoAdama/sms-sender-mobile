package com.example.sms_sender.ui.components.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.sms_sender.R
import com.example.sms_sender.service.setting.Setting

@Composable
fun ScheduleSelect(
    modifier: Modifier = Modifier,
    value: Int = Setting.SCHEDULE_TIME_DEFAULT_VALUE,
    onSelect: (value: Int) -> Unit = {}
){

    val choices = mapOf(
        "5 secs" to 5_000,
        "10 secs" to 10_000,
        "30 secs" to 30_000,
        "1 minute" to 60_000,
        "2 minutes" to 120_000,
        "5 minutes" to 300_000
    )

    SelectComponent(
        modifier = modifier,
        title = stringResource(R.string.retreive_frequency),
        data = choices,
        defaultValue = choices.entries.firstOrNull { it.value == value }?.key,
        onChange = onSelect
    )
}