package com.example.sms_sender.data.repository

import com.example.sms_sender.model.Setting
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getSetting(): Flow<List<Setting>>
    suspend fun update(setting: Setting)
}