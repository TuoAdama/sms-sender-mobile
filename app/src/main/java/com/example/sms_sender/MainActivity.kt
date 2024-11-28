package com.example.sms_sender

import SettingForm
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.service.setting.SmsService
import com.example.sms_sender.service.setting.SettingKey
import com.example.sms_sender.viewmodel.SettingViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val dataStore: DataStoreService by lazy {
        DataStoreService(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val settingViewModel by viewModels<SettingViewModel>();

        lifecycleScope.launch {
            settingViewModel.apiURL = dataStore.getString(SettingKey.API_URL_KEY) ?: settingViewModel.apiURL
            settingViewModel.country = dataStore.getString(SettingKey.COUNTRY_KEY) ?: settingViewModel.apiURL

            val intent = Intent(this@MainActivity, SmsService::class.java)
            intent.putExtra("API_URL", settingViewModel.apiURL);
            startService(intent);
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