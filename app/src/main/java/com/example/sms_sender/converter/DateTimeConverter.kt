package com.example.sms_sender.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDateTime()
        }
    }

    @TypeConverter
    fun toTimestamp(value: LocalDateTime?): Long? {
        return value?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
    }
}