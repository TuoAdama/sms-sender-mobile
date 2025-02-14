package com.example.sms_sender.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sms_sender.App
import com.example.sms_sender.data.repository.SmsDataRepository
import com.example.sms_sender.model.SmsData
import com.example.sms_sender.network.SmsApi
import com.example.sms_sender.network.SmsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SmsService : Service() {

    private val SMS_LOGGER_TAG = "SMS-SERVICE"

    companion object SettingKey {
        val COUNTRY_KEY = "COUNTRY"
        const val API_URL_KEY = "API_URL"
        const val API_IS_AUTHENTICATED = "API_IS_AUTHENTICATED"
        const val API_TOKEN = "API_TOKEN";
    }

    private var isActive = true;

    private val smsManager: SmsManager = SmsManager.getDefault();


    private lateinit var smsDataRepository: SmsDataRepository;

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(intent: Intent?){

        val appContainer  = (this.application as App).appContainer

        smsDataRepository = appContainer.smsDataRepository

        var baseUrl = intent?.getStringExtra(API_URL_KEY)!!
        val isAuth = intent.getBooleanExtra(API_IS_AUTHENTICATED, false)
        val authValue = intent.getStringExtra(API_TOKEN)!!

            baseUrl = if( baseUrl.last() == '/' ) baseUrl else baseUrl.plus("/");

            CoroutineScope(Dispatchers.Default).launch {
                while (isActive){
                    val smsApiService = SmsApi.retrofitService(baseUrl)
                    val messages = if (isAuth) smsApiService.getSms(authValue) else smsApiService.getSms()

                    Log.i(SMS_LOGGER_TAG, baseUrl);

                    messages.forEachIndexed { index: Int, message: SmsResponse ->
                        Log.i(SMS_LOGGER_TAG, "running... $index");
                        notification(index, messages.size)
                        sendMessage(message)
                        smsDataRepository.insert(
                            SmsData(recipient = message.recipient, message = message.message)
                        )
                    }
                    delay(5000)
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

    override fun onDestroy() {
        Log.i(SMS_LOGGER_TAG, "sms service stopped")
        isActive = false
    }
}