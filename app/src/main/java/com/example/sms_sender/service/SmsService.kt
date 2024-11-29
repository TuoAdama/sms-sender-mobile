package com.example.sms_sender.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.app.NotificationCompat
import com.example.sms_sender.model.Message
import com.example.sms_sender.util.ApiRequestHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SmsService : Service() {

    companion object {
        const val API_URL_EXTRA = "API_URL"
    }

    enum class ACTION {
        START, STOP
    }

    private var isActive = true;

    private lateinit var job: Job;

    private val apiRequest = ApiRequestHandler()

    private val smsManager: SmsManager = SmsManager.getDefault();

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action){
            ACTION.START.name -> start(intent)
            ACTION.STOP.name -> stop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(intent: Intent?){

            val apiURL = intent?.getStringExtra(API_URL_EXTRA) ?: throw Exception("API URL NOT DEFINED")

            job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive){
                val messages = apiRequest.getAvailableMessages(apiURL)
                Log.i("NEW_SERVICE", "running...");
                messages.forEachIndexed { index: Int, message: Message ->
                    Log.i("NEW_SERVICE", "running... $index");
                    notification(index, messages.size)
                    sendMessage(message)
                }
                Thread.sleep(2000L)
            }
        }
    }


    private fun notification(index: Int, total: Int){
        val smsNotification = NotificationCompat
            .Builder(this, "sms_notification",)
            .setContentTitle("Sms sending")
            .setContentText("Sending SMS $index/$total")
            .build()

        startForeground(1, smsNotification)
    }


    private fun sendMessage(message: Message) {
        smsManager.sendTextMessage(message.recipient, null, message.message, null, null);
    }

    private fun stop(){
        isActive = false
        job.cancel()
        stopSelf()
    }
}