package com.example.sms_sender.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R
import com.example.sms_sender.ui.screen.SettingUiState
import com.example.sms_sender.ui.screen.SettingViewModel
import com.example.sms_sender.util.ColorUtils

@Composable
fun SmsServiceAction (
    settingUiState: SettingUiState,
    onStartService: () -> Unit,
    onStopService: () -> Unit,
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            onClick = onStartService,
            colors = ButtonColors(ColorUtils.greenColors, Color.Black, ColorUtils.greenColorsDisabled, Color.Black),
            enabled = !settingUiState.isRunning
        )
        {
            Text(stringResource(R.string.form_start_btn))
        }
        Button(
            onClick = onStopService,
            modifier = Modifier.padding(10.dp, 0.dp),
            colors = ButtonColors(ColorUtils.redColors, Color.Black, ColorUtils.redColorsDisabled, Color.Black),
            enabled = settingUiState.isRunning
        ) {
            Text(stringResource(R.string.form_stop_btn))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmsServiceActionPreview(){
    SmsServiceAction(
        settingUiState = SettingViewModel().settingUiState,
        onStopService = {},
        onStartService = {}
    )
}