package com.example.sms_sender.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.sms_sender.App
import com.example.sms_sender.data.repository.SmsDataRepository
import com.example.sms_sender.exception.UndefinedSmsServiceKeyException
import com.example.sms_sender.model.SmsData
import com.example.sms_sender.network.SmsApi
import com.example.sms_sender.network.SmsResponse
import com.example.sms_sender.service.setting.Setting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDateTime

class SmsService : Service() {

    private val SMS_LOGGER_TAG = "SMS-SERVICE"

    private var isActive = true;

    private val smsManager: SmsManager = SmsManager.getDefault();


    private lateinit var smsDataRepository: SmsDataRepository;

    override fun onBind(p0: Intent?): IBinder? {
        return null;
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val appContainer  = (this.application as App).appContainer
        smsDataRepository = appContainer.smsDataRepository
        start(intent)
        startSmsSending()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(intent: Intent?){
        var baseUrl = intent?.getStringExtra(Setting.API_URL_KEY) ?: throw UndefinedSmsServiceKeyException("API URL key is not defined")
        val isAuth = intent.getBooleanExtra(Setting.API_IS_AUTHENTICATED_KEY, false)
        val authValue = intent.getStringExtra(Setting.API_TOKEN_KEY) ?: throw UndefinedSmsServiceKeyException("API Token key is not defined")
        val scheduleTime = intent.getIntExtra(Setting.SCHEDULE_TIME_KEY, Setting.SCHEDULE_TIME_DEFAULT_VALUE)

        baseUrl = if( baseUrl.last() == '/' ) baseUrl else baseUrl.plus("/")

            CoroutineScope(Dispatchers.Default).launch {
                while (isActive){
                    val smsApiService = SmsApi.retrofitService(baseUrl)
                    try {
                        val messages = if (isAuth) smsApiService.getSms(authValue) else smsApiService.getSms()
                        Log.i(SMS_LOGGER_TAG, baseUrl);

                        messages.forEachIndexed { index: Int, message: SmsResponse ->
                            Log.i(SMS_LOGGER_TAG, "running... $index");

                            notification(index, messages.size)

                            smsDataRepository.insert(
                                SmsData(
                                    recipient = message.recipient,
                                    message = message.message,
                                )
                            )
                        }
                    } catch (e: IOException){
                        Log.i(SMS_LOGGER_TAG, e.message ?: "Network is down")
                    }

                    delay(scheduleTime.toLong())
                }
        }
    }


    private fun startSmsSending(){
        CoroutineScope(Dispatchers.Default).launch {
            smsDataRepository.getUnsentItems().collect {
                sendMessage(it)
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


    private fun sendMessage(messages: List<SmsData>) {
        messages.forEach {
            smsManager.sendTextMessage(it.recipient, null, it.message, null, null)
            Log.i(SMS_LOGGER_TAG, "[sms sent]: $it")
            smsDataRepository.update(it.copy(sent = true, updatedAt = LocalDateTime.now()))
        }
    }

    override fun onDestroy() {
        Log.i(SMS_LOGGER_TAG, "sms service stopped")
        isActive = false
    }
}