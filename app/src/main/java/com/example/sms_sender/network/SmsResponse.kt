package com.example.sms_sender.network

import kotlinx.serialization.Serializable

@Serializable
data class SmsResponse(
    val id: Int,
    val recipient: String,
    val message: String
)
