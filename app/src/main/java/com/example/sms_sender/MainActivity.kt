package com.example.sms_sender

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.service.CountryCodeHandler
import com.example.sms_sender.ui.components.CountryChoice
import com.example.sms_sender.ui.theme.SmssenderTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //startService(Intent(this, SmsService::class.java));

        enableEdgeToEdge()
        setContent {
            MessageForm()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageForm() {
    var country by remember { mutableStateOf("") }
    SmssenderTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Paramètre")
                    }
                )
            },
            content = { padding ->
                Column (
                    modifier = Modifier.fillMaxWidth()
                        .padding(20.dp, 80.dp)
                ) {
                    val code = CountryCodeHandler.getCode(country);
                    if (code != null){
                        Text(text = code.toString())
                    }
                    Column {
                        Text(text = "Pays", modifier = Modifier.padding(0.dp, 10.dp))
                        CountryChoice{ value -> country = value }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmssenderTheme {
        MessageForm()
    }
}