package com.example.sms_sender.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header


interface SmsApiService{
    @GET("api/messages")
    suspend fun getSms(@Header("Authorization") authorization: String): List<SmsResponse>

    @GET("api/messages")
    suspend fun getSms(): List<SmsResponse>
}


object SmsApi {
    fun retrofitService(baseUrl: String) : SmsApiService{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
        return retrofit.create(SmsApiService::class.java)
    }
}