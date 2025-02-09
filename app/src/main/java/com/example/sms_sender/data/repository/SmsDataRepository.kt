package com.example.sms_sender.data.repository

import com.example.sms_sender.model.SmsData
import kotlinx.coroutines.flow.Flow

interface SmsDataRepository {
    fun getItems(): Flow<List<SmsData>>
    suspend fun insert(smsData: SmsData)
}