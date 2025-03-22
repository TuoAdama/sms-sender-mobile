package com.example.sms_sender.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sms_sender.model.SmsData
import kotlinx.coroutines.flow.Flow

@Dao
interface SmsDataDao {
    @Query("SELECT * FROM sms_data ORDER BY createdAt DESC")
    fun getAllItems(): Flow<List<SmsData>>

    @Query("SELECT * FROM sms_data WHERE sent = 0 ORDER BY createdAt DESC")
    fun getAllUnsentItems(): Flow<List<SmsData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(smsData: SmsData)

    @Query("SELECT COUNT(*) FROM sms_data")
    fun count(): Flow<Int>

    @Query("SELECT COUNT(*) FROM sms_data WHERE sent = 1")
    fun getCountSmsSent(): Flow<Int>

    @Query("SELECT COUNT(*) FROM sms_data WHERE sent = 0")
    fun getCountUnSmsSent(): Flow<Int>

    @Update
    fun update(smsData: SmsData): Int

    @Delete
    fun delete(smsData: SmsData): Void
}