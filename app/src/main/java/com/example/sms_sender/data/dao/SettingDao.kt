package com.example.sms_sender.data.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sms_sender.model.Setting
import kotlinx.coroutines.flow.Flow


@Dao
interface SettingDao {

    @Query("SELECT * FROM setting")
    fun getSetting(): Flow<List<Setting>>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun update(smsData: Setting)
}