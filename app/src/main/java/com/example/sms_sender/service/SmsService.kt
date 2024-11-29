package com.example.sms_sender.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SmsService : Service() {

    enum class ACTION {
        START, STOP
    }

    private var isActive = true;

    private lateinit var job: Job;

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action){
            ACTION.START.name -> start()
            ACTION.STOP.name -> stop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){
            notification()
            job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive){
                Log.i("NEW_SERVICE", "running...");
                Thread.sleep(2000L)
            }
        }
    }


    private fun notification(){
        val smsNotification = NotificationCompat
            .Builder(this, "sms_notification",)
            .setContentTitle("SMS Notification")
            .setContentText("SMS notification Context")
            .build()

        startForeground(1, smsNotification)
    }

    private fun stop(){
        isActive = false
        job.cancel()
        stopSelf()
    }
}