package com.example.sms_sender.service

import com.example.sms_sender.enumaration.RoutePath
import com.example.sms_sender.model.Message
import java.net.URL

class ApiRequestService {

    fun getAvailableMessages(apiURL: String): List<Message> {
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