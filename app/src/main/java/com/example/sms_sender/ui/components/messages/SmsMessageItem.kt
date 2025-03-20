package com.example.sms_sender.ui.components.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R
import com.example.sms_sender.model.SmsData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun MessageItem(smsData: SmsData){

    val paintResource = painterResource(if (smsData.sent) R.drawable.sms_sent else R.drawable.sms_unsent);

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
            Image(
                painter = paintResource,
                contentDescription = "person icon",
                modifier = Modifier
                    .clip(CircleShape)
                    .padding(10.dp)
            )
        },
        trailingContent = {
            val updatedAt = smsData.updatedAt;
            var formatPattern = "HH:mm"
            if (LocalDateTime.now().isBefore(updatedAt)) {
                formatPattern = "dd/MM/yyyy"
            }
            Text(updatedAt.format(DateTimeFormatter.ofPattern(formatPattern)))
        }
    )
}

@Composable
@Preview(showBackground = true)
fun MessageItemPreview(){
    MessageItem(SmsData(1, "0751097177", "Bonjour", createdAt = LocalDateTime.now(), updatedAt = LocalDateTime.now()))
}