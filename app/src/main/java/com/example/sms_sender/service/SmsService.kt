package com.example.sms_sender.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sms_sender.App
import com.example.sms_sender.model.Message
import com.example.sms_sender.service.setting.SettingKey
import com.example.sms_sender.util.ApiRequestHandler
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

            val apiURL = intent?.getStringExtra(SettingKey.API_URL_KEY) ?: throw Exception("API URL NOT DEFINED")
            val isAuth = intent.getBooleanExtra(SettingKey.API_IS_AUTHENTICATED, false);
            val authHeader = intent.getStringExtra(SettingKey.API_AUTHORISATION_HEADER).toString()
            val authValue = intent.getStringExtra(SettingKey.API_TOKEN).toString()

            val headers = HashMap<String, String>()
            if (isAuth){
                headers[authHeader] = authValue
            }

            job = CoroutineScope(Dispatchers.Default).launch {
            while (isActive){
                val messages = apiRequest.getAvailableMessages(apiURL, headers)
                Log.i("NEW_SERVICE", "isAuth: $isAuth, authHeader: $authHeader, authValue: $authValue apiUrl: $apiURL, headers: $headers")
                Log.i("NEW_SERVICE", "running... message $messages");
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
            .Builder(this, App.SMS_NOTIFICATION_CHANNEL_ID)
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