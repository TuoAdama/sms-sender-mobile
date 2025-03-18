package com.example.sms_sender.data.repository

import com.example.sms_sender.data.dao.SmsDataDao
import com.example.sms_sender.model.SmsData
import kotlinx.coroutines.flow.Flow

class SmsDataRepositoryImpl(private val smsDataDao: SmsDataDao) : SmsDataRepository {

    override fun getItems(): Flow<List<SmsData>> = smsDataDao.getAllItems()
    override suspend fun insert(smsData: SmsData)  = smsDataDao.insert(smsData)
    override fun count(): Flow<Int>  = smsDataDao.count()

    override fun getCountSmsSent(): Flow<Int>  = smsDataDao.getCountSmsSent()
    override fun getCountUnSmsSent(): Flow<Int>  = smsDataDao.getCountUnSmsSent()

    override fun getUnsentItems(): Flow<List<SmsData>> = smsDataDao.getAllUnsentItems()

    override fun update(smsData: SmsData): Int = smsDataDao.update(smsData)

    override suspend fun delete(smsData: SmsData): Void = smsDataDao.delete(smsData)

}