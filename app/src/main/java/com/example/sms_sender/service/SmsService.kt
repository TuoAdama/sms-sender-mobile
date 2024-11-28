package com.example.sms_sender.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class SmsService : Service() {

    enum class ACTION {
        START, STOP
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when (intent?.action){
            ACTION.START.name -> start()
            ACTION.STOP.name -> start()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(){

    }

    private fun stop(){
        stopSelf()
    }
}