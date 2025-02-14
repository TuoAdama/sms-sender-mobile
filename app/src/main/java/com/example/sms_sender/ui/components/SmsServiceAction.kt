package com.example.sms_sender.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sender.R
import com.example.sms_sender.model.Setting
import com.example.sms_sender.smsServiceIsRunning
import com.example.sms_sender.startSmsService
import com.example.sms_sender.stopSmsService
import com.example.sms_sender.ui.screen.setting.SettingViewModel
import com.example.sms_sender.util.ColorUtils
import kotlin.random.Random

@Composable
fun SmsServiceAction (
    onStartService: () -> Unit = {},
    onStopService: () -> Unit = {},
    isServiceRunning: Boolean = false,
    enabled: Boolean = false,
){
    if (!isServiceRunning){
        IconButton(onClick = onStartService, enabled = enabled) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = stringResource(R.string.setting),
                modifier = Modifier.size(35.dp),
            )
        }
    }else{
        IconButton(onClick = onStopService, enabled = enabled) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(R.string.setting),
                modifier = Modifier.size(35.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmsServiceActionPreview(){
    SmsServiceAction()
}