package com.example.sms_sender

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Applier
import com.example.sms_sender.container.AppContainer
import com.example.sms_sender.container.AppDataContainer

class App : Application() {

    lateinit var appContainer: AppContainer;

    companion object {
        const val SMS_NOTIFICATION_CHANNEL_ID = "sms_notification"
    }

    override fun onCreate() {
        super.onCreate()

        appContainer = AppDataContainer(this)

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