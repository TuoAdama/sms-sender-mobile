package com.example.sms_sender.data.repository

import com.example.sms_sender.data.dao.SettingDao
import com.example.sms_sender.model.Setting
import kotlinx.coroutines.flow.Flow

class SettingRepositoryImpl(private val settingDao: SettingDao) : SettingRepository {
    override fun getSetting(): Flow<List<Setting>> = settingDao.getSetting()

    override suspend fun update(setting: Setting) = settingDao.update(setting)
}