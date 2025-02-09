package com.example.sms_sender.container

import com.example.sms_sender.data.repository.SmsDataRepository

interface AppContainer {
    val smsDataRepository: SmsDataRepository;
}