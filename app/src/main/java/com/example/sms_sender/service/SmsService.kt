package com.example.sms_sender.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sender.App
import com.example.sms_sender.data.repository.SmsDataRepository
import com.example.sms_sender.data.repository.SmsDataRepositoryImpl
import com.example.sms_sender.model.SmsData
import com.example.sms_sender.network.SmsApi
import com.example.sms_sender.network.SmsResponse
import com.example.sms_sender.service.setting.SettingKey
import com.example.sms_sender.ui.sms.SmsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SmsService : Service() {

    enum class ACTION {
        START, STOP
    }

    private var isActive = true;

    private lateinit var job: Job;

    private val smsManager: SmsManager = SmsManager.getDefault();


    private lateinit var smsDataRepository: SmsDataRepository;

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

            var baseUrl = intent?.getStringExtra(SettingKey.API_URL_KEY) ?: throw Exception("API URL NOT DEFINED")
            val isAuth = intent.getBooleanExtra(SettingKey.API_IS_AUTHENTICATED, false);
            val authValue = intent.getStringExtra(SettingKey.API_TOKEN).toString()

        smsDataRepository = (this.application as App).appContainer.smsDataRepository

            baseUrl = if( baseUrl.last() == '/' ) baseUrl else baseUrl.plus("/");

            job = CoroutineScope(Dispatchers.Default).launch {
                while (isActive){

                    val smsApiService = SmsApi.retrofitService(baseUrl)
                    val messages = if (isAuth) smsApiService.getSms(authValue) else smsApiService.getSms()

                    messages.forEachIndexed { index: Int, message: SmsResponse ->
                        Log.i("NEW_SERVICE", "running... $index");
                        notification(index, messages.size)
                        // sendMessage(message)
                        smsDataRepository.insert(
                            SmsData(recipient = message.recipient, message = message.message)
                        )
                    }
                    delay(2000)
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


    private fun sendMessage(message: SmsResponse) {
        smsManager.sendTextMessage(message.recipient, null, message.message, null, null);
    }

    private fun stop(){
        isActive = false
        job.cancel()
        stopSelf()
    }
}