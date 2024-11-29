package com.example.sms_sender

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Applier

class App : Application() {

    companion object {
        const val SMS_NOTIFICATION_CHANNEL_ID = "sms_notification"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("SERVICE-RUNNING", "NOTIFICATION CHANNEL CREATED")

        val smsChannel = NotificationChannel(
            SMS_NOTIFICATION_CHANNEL_ID,
            "SMS Notification",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(smsChannel)
    }
}