package com.example.sms_sender.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sms_sender.data.dao.SettingDao
import com.example.sms_sender.data.dao.SmsDataDao
import com.example.sms_sender.model.Setting
import com.example.sms_sender.model.SmsData


@Database(entities = [SmsData::class, Setting::class], version = 2, exportSchema = true)
abstract class SmsSenderDatabase : RoomDatabase() {
    abstract fun getSmsDataDao(): SmsDataDao
    abstract fun getSettingDao(): SettingDao

    companion object {
        private const val DATABASE_NAME = "sms_database";

        @Volatile
        private var instance: SmsSenderDatabase? = null;

        fun getDataBase(context: Context): SmsSenderDatabase {
            return instance ?: synchronized(this){
                Room.databaseBuilder(context, SmsSenderDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}