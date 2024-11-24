package com.example.sms_sender.service

import com.example.sms_sender.enumaration.RoutePath
import com.example.sms_sender.model.Message
import java.net.URL

class ApiRequestService {

    fun getAvailableMessages(): List<Message> {
        return listOf(
            Message("0751097177", "Bonjour Demsi"),
            Message("0751097177", "Votre code de confication est 00933"),
            Message("0751097177", "Petit bonjour de ta part"),
            Message("0751097177", "Hello, tu vas bien ?"),
            Message("0751097177", "Ça va bien à Abidjan ?"),
            Message("0751097177", "Je suis la loit"),
        )
    }
}