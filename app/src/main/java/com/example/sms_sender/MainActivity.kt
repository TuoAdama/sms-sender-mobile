package com.example.sms_sender

import SettingForm
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.example.sms_sender.service.SmsService
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

        startService(Intent(this, SmsService::class.java));

        lifecycleScope.launch {
            settingViewModel.apiURL = dataStore.getString(SettingKey.API_URL_KEY) ?: settingViewModel.apiURL
            settingViewModel.country = dataStore.getString(SettingKey.COUNTRY_KEY) ?: settingViewModel.apiURL
        }

        enableEdgeToEdge()
        setContent {
            SettingForm(
                initData = settingViewModel,
                onSubmit = {formData -> formData.forEach {(key, value) ->
                lifecycleScope.launch {
                    dataStore.saveString(key, value)
                    Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show();
                }
            }})
        }
    }
}