package com.example.sms_sender.util

import android.util.Log
import com.example.sms_sender.model.Message
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection
import java.net.URL

class ApiRequestHandler {

    fun getAvailableMessages(apiURL: String, headers: Map<String, String> = HashMap()): List<Message> {
        val gson = Gson()
        var messages = listOf<Message>()
        try {
            val url = URL(apiURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            headers.forEach { (key, value) ->
                connection.setRequestProperty(key, value)
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val response = inputStream.bufferedReader().use { it.readText() }

                val listType = object : TypeToken<List<Message>>() {}.type
                messages = gson.fromJson(response, listType)

                Log.i("API-SMS", response)

            } else {
                println("Failed to fetch data: $responseCode")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return messages
    }
}