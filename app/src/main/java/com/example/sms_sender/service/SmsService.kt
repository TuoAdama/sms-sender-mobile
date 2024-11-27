package com.example.sms_sender.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Process
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast

class SmsService : Service() {

    private var serviceHandler: ServiceHandler? = null;
    private var serviceLopper: Looper? = null;
    private val apiRequestService: ApiRequestService = ApiRequestService();
    private lateinit var smsApiURL: String;

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {
        val smsManager: SmsManager = SmsManager.getDefault();

        override fun handleMessage(msg: Message) {
            while (true) {
                Thread.sleep(3000);
                apiRequestService.getAvailableMessages(apiURL = smsApiURL).forEach { sms ->
                    Log.i("SMS-SENDING", "id: ${sms.id} number: ${sms.recipient}, message: ${sms.message}")
                   // smsManager.sendTextMessage(sms.recipient, null, sms.message, null, null);
                }
            }
        }
    }

    override fun onCreate() {
        HandlerThread("smsService", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start();
            serviceLopper = looper;
            serviceHandler = ServiceHandler(serviceLopper!!);
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        smsApiURL = intent?.getStringExtra("API_URL") ?: throw Exception("API URL NOT DEFINED")
        Log.i("SMS-ENDING", smsApiURL)

        Toast.makeText(this, "Service starting", Toast.LENGTH_SHORT).show();
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }
        return START_STICKY
    }
}