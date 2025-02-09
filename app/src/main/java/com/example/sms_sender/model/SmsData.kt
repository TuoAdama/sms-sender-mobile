package com.example.sms_sender.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_data")
data class SmsData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val recipient: String,
    val message: String,
    val sent: Boolean = false
)
