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

fun Context.getActivityOrNull(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}

fun Context.startSmsService(setting: Setting) {
    Intent(this, SmsService::class.java)
        .also {
            it.action = SmsService.ACTION.START.name
            it.putExtra(SmsService.API_URL_KEY, setting.domain)
            it.putExtra(SmsService.API_IS_AUTHENTICATED, setting.isAuthenticated)
            it.putExtra(SmsService.API_TOKEN, setting.token)
            startService(it)
        }
}

fun Context.stopSmsService() {
    Intent(this, SmsService::class.java)
        .also {
            it.action = SmsService.ACTION.STOP.name
            startService(it)
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