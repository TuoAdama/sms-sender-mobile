package com.example.sms_sender.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sms_sender.model.SmsData

@Composable
fun SmsMessageList(
    messages: List<SmsData>
){
    Column {
        messages.forEach {
            SmsMessageItem(smsData = it, modifier = Modifier.padding(0.dp, 10.dp))
            HorizontalDivider()
        }
    }
}


@Composable
fun SmsMessageItem(modifier: Modifier = Modifier, smsData: SmsData){
    Column(modifier = modifier) {
        Text(smsData.recipient)
        Text(smsData.message)
        Text(smsData.sent.toString())
    }
}