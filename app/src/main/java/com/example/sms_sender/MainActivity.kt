package com.example.sms_sender

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
import com.example.sms_sender.ui.navigation.SmsSenderApp
import com.example.sms_sender.ui.screen.setting.SettingViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestSmsSendingPermission()
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.TIRAMISU){
            requestNotificationPermission();
        }

        enableEdgeToEdge()
        setContent {
            SmsSenderApp()
        }
    }

    private fun onStopService() {
        Intent(this@MainActivity, SmsService::class.java)
            .also {
                it.action = SmsService.ACTION.STOP.name
                startService(it)
            }
        //settingViewModel.setRunning(false)
    }

    private fun onStartSmsService(){
        //val setting = settingViewModel.settingUiState;

//        Intent(this@MainActivity, SmsService::class.java)
//            .also {
//                it.action = SmsService.ACTION.START.name
//                Log.i("MainActivity", "isAuth: ${setting.isAuthenticated}")
//                it.putExtra(SettingKey.API_URL_KEY, setting.apiURL)
//                it.putExtra(SettingKey.API_IS_AUTHENTICATED, setting.isAuthenticated)
//                it.putExtra(SettingKey.API_AUTHORISATION_HEADER, setting.authenticationHeader)
//                it.putExtra(SettingKey.API_TOKEN, setting.token)
//                startService(it)
//            }
        //settingViewModel.setRunning(true)
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

    private fun requestSmsSendingPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS),
            100
        )
    }
}