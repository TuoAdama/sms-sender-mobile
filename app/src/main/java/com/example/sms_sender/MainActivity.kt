package com.example.sms_sender

import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.service.SmsService
import com.example.sms_sender.ui.theme.SmssenderTheme


class MainActivity : ComponentActivity() {

    private val onSendSms: (String) -> Unit = { message ->
        val smsManager: SmsManager = SmsManager.getDefault();
        smsManager.sendTextMessage("0751097177", null, message, null, null);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startService(Intent(this, SmsService::class.java));

        enableEdgeToEdge()
        setContent {
            MessageForm(onSendSms)
        }
    }
}

@Composable
fun MessageForm(onSubmit: (message: String) -> Unit) {
    var message by remember { mutableStateOf("") }

    SmssenderTheme {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Entrez votre message",
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = message,
                onValueChange = { message = it },
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button( onClick =  {
                onSubmit(message);
            }) {
                Text("Envoyer")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmssenderTheme {
        MessageForm { message: String -> println(message) }
    }
}