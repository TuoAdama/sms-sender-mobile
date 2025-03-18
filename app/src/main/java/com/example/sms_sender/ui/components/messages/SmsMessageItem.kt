package com.example.sms_sender.ui.components.messages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.model.SmsData


@Composable
fun MessageItem(smsData: SmsData){
    ListItem(
        modifier = Modifier.clip(MaterialTheme.shapes.small),
        headlineContent = {
            Text(
                smsData.recipient,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Column {
                Text(
                    smsData.message,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.Sms,
                contentDescription = "person icon",
                Modifier
                    .clip(CircleShape)
                    .padding(10.dp)
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
fun MessageItemPreview(){
    MessageItem(SmsData(1, "0751097177", "Bonjour"))
}