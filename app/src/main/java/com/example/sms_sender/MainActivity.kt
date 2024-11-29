package com.example.sms_sender

import SettingForm
import android.Manifest
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.service.SmsService
import com.example.sms_sender.service.setting.SettingKey
import com.example.sms_sender.viewmodel.SettingViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    private val dataStore: DataStoreService by lazy {
        DataStoreService(this)
    }

    private val settingViewModel by viewModels<SettingViewModel>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.TIRAMISU){
            requestNotificationPermission();
        }
        settingViewModel.isRunning = this.smsServiceIsRunning()
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
                    Toast.makeText(this@MainActivity, "Enregistré", Toast.LENGTH_SHORT).show();
                }
                }},
                onStartService = {

                    Intent(this@MainActivity, SmsService::class.java)
                        .also {
                            it.action = SmsService.ACTION.START.name
                            it.putExtra(SmsService.API_URL_EXTRA, settingViewModel.apiURL)
                            startService(it)
                        }

                    settingViewModel.isRunning = true
                },
                onStopService = {

                    Intent(this@MainActivity, SmsService::class.java)
                        .also {
                            it.action = SmsService.ACTION.STOP.name
                            startService(it)
                        }

                    settingViewModel.isRunning = false
                }
            )
        }
    }


    private fun smsServiceIsRunning(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val service = activityManager.getRunningServices(Integer.MAX_VALUE)
        service.forEach { s -> Log.i("SERVICE-RUNNING", s.service.className) }

        return service.find { s ->
             s.service.className == SmsService::class.java.name
         } != null
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            100
        )
    }
}