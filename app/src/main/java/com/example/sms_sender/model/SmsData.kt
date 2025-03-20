package com.example.sms_sender.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "sms_data")
data class SmsData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val recipient: String,
    val message: String,
    val sent: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
