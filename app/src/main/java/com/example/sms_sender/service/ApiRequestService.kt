package com.example.sms_sender.service

import android.util.Log
import com.example.sms_sender.enumaration.RoutePath
import com.example.sms_sender.model.Message
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.HttpURLConnection
import java.net.URL

class ApiRequestService {

    data class Post(
        val id: Int,
        val title: String,
        val body: String
    )

    fun getAvailableMessages(apiURL: String): List<Message> {
        val gson = Gson()
        try {
            val url = URL(apiURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.setRequestProperty("Authorization", "Bearer ")

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val response = inputStream.bufferedReader().use { it.readText() }

                val listType = object : TypeToken<List<Message>>() {}.type
                gson.fromJson<List<Message>>(response, listType).forEach { post ->
                    Log.i("HTTP-REQUEST", "recipient: ${post.recipient} message: ${post.message}")
                }
            } else {
                println("Failed to fetch data: $responseCode")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return listOf(
            Message(1, "0751097177", "Bonjour Demsi"),
            Message(2, "0751097177", "Votre code de confication est 00933"),
            Message(3, "0751097177", "Petit bonjour de ta part"),
            Message(4, "0751097177", "Hello, tu vas bien ?"),
            Message(5, "0751097177", "Ça va bien à Abidjan ?"),
            Message(6, "0751097177", "Je suis la loit"),
        )
    }
}