package com.example.sms_sender

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.sms_sender.model.Setting
import com.example.sms_sender.service.SmsService
import com.example.sms_sender.ui.navigation.SmsSenderApp
import com.example.sms_sender.ui.screen.setting.SettingUiState


class MainActivity : ComponentActivity() {

    companion object{
        var smsServiceIntent: Intent? =null;
    }

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

fun Context.startSmsService(setting: SettingUiState) {
    MainActivity.smsServiceIntent = Intent(this, SmsService::class.java)
        .also {
            it.putExtra(SmsService.API_URL_KEY, setting.apiURL)
            it.putExtra(SmsService.API_IS_AUTHENTICATED, setting.isAuthenticated)
            it.putExtra(SmsService.API_TOKEN, setting.token)
            it.putExtra(SmsService.SCHEDULE_TIME, setting.scheduleTime)
            startService(it)
        }
}

fun Context.restartSmsService(setting: SettingUiState) {
    this.stopSmsService()
    this.startSmsService(setting)
}

fun Context.stopSmsService() {
    if (MainActivity.smsServiceIntent !== null){
        stopService(MainActivity.smsServiceIntent)
    }
}

fun Context.smsServiceIsRunning(): Boolean{
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val service = activityManager.getRunningServices(Integer.MAX_VALUE)
    service.forEach { s -> Log.i("SERVICE-RUNNING", s.service.className) }

    return service.find { s ->
        s.service.className == SmsService::class.java.name
    } != null
}