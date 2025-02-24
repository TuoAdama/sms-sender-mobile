package com.example.sms_sender.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R

@Composable
fun SmsServiceAction (
    onStartService: () -> Unit = {},
    onStopService: () -> Unit = {},
    isServiceRunning: Boolean = false,
    enabled: Boolean = false,
){
    if (!isServiceRunning){
        IconButton(onClick = onStartService, enabled = enabled) {
            Image(
                painter = painterResource(
                    if(enabled) R.drawable.play_icon else R.drawable.play_disable_icon
                ),
                contentDescription = stringResource(R.string.setting),
                modifier = Modifier.size(35.dp),
            )
        }
    }else{
        IconButton(onClick = onStopService, enabled = enabled) {
            Image(
                painter = painterResource(R.drawable.stop_icon),
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