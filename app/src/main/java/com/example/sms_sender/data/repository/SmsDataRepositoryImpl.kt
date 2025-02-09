package com.example.sms_sender.data.repository

import com.example.sms_sender.data.dao.SmsDataDao
import com.example.sms_sender.model.SmsData
import kotlinx.coroutines.flow.Flow

class SmsDataRepositoryImpl(private val smsDataDao: SmsDataDao) : SmsDataRepository {

    override fun getItems(): Flow<List<SmsData>> = smsDataDao.getAllItems()
    override suspend fun insert(smsData: SmsData)  = smsDataDao.insert(smsData)

}