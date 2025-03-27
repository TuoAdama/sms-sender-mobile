package com.example.sms_sender.service.setting

object Setting {
    const val SCHEDULE_TIME_DEFAULT_VALUE =  10_000 // 10 secs
    const val SCHEDULE_TIME_KEY =  "SCHEDULE_TIME"
    const val COUNTRY_KEY = "COUNTRY"
    const val API_URL_KEY = "API_URL"
    const val API_IS_AUTHENTICATED_KEY = "API_IS_AUTHENTICATED"
    const val API_TOKEN_KEY = "API_TOKEN";
    const val SMS_BETWEEN_EACH_SENDING_SMS = 5_000L; // 10s
}