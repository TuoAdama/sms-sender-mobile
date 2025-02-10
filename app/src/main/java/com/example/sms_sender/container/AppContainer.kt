package com.example.sms_sender.container

import com.example.sms_sender.data.repository.SettingRepository
import com.example.sms_sender.data.repository.SmsDataRepository
import com.example.sms_sender.service.DataStoreService

interface AppContainer {
    val smsDataRepository: SmsDataRepository;
    val settingRepository: SettingRepository
}