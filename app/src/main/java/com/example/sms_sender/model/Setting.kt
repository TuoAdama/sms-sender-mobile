package com.example.sms_sender.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "setting")
data class Setting(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val domain: String,
    val country: String,
    val isAuthenticated: Boolean,
    val token: String = "",
)