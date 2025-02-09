package com.example.sms_sender.container

import android.content.Context
import com.example.sms_sender.data.SmsSenderDatabase
import com.example.sms_sender.data.repository.SmsDataRepository
import com.example.sms_sender.data.repository.SmsDataRepositoryImpl

class AppDataContainer(context: Context) : AppContainer {
    override val smsDataRepository: SmsDataRepository by lazy {
        SmsDataRepositoryImpl(SmsSenderDatabase.getDataBase(context).getSmsDataDao())
    }
}