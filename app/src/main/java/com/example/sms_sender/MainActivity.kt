package com.example.sms_sender

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.example.sms_sender.service.CountryCodeHandler
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.service.setting.SettingKey
import com.example.sms_sender.ui.components.CountryChoice
import com.example.sms_sender.ui.theme.SmssenderTheme
import com.example.sms_sender.viewmodel.SettingViewModel
import kotlinx.coroutines.launch
import kotlin.math.log


class MainActivity : ComponentActivity() {

    private val dataStore: DataStoreService by lazy {
        DataStoreService(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingViewModel by viewModels<SettingViewModel>();

        //startService(Intent(this, SmsService::class.java));

        lifecycleScope.launch {
            settingViewModel.apiURL = dataStore.getString(SettingKey.API_URL_KEY) ?: ""
            settingViewModel.country = dataStore.getString(SettingKey.COUNTRY_KEY) ?: ""
        }

        enableEdgeToEdge()
        setContent {
            SettingForm(
                initData = settingViewModel,
                onSubmit = {formData -> formData.forEach {(key, value) ->
                lifecycleScope.launch {
                    dataStore.saveString(key, value)
                }
            }})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingForm(initData: SettingViewModel = SettingViewModel(), onSubmit: (data: Map<String, String>) -> Unit = {data -> println(data.size)}) {

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
                    Column {
                        Text(text = "Pays", modifier = Modifier.padding(0.dp, 10.dp))
                        CountryChoice{ value -> initData.country = value }

                        Spacer(Modifier.padding(0.dp, 10.dp))

                        Text("Api URL")
                        TextField(value = initData.apiURL, onValueChange = {value -> initData.apiURL = value})

                        Spacer(Modifier.padding(0.dp, 10.dp))

                        Button(onClick = {
                            val data = HashMap<String, String>().apply{
                                set(SettingKey.API_URL_KEY, initData.apiURL)
                                set(SettingKey.COUNTRY_KEY, initData.country)
                            }
                            onSubmit(data);
                        }) {
                            Text("Enregistrer")
                        }
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
        SettingForm()
    }
}