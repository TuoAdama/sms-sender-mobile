package com.example.sms_sender.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sms_sender.service.CountryCodeHandler

class SettingViewModel : ViewModel() {
    var country by mutableStateOf(CountryCodeHandler.FRANCE)
    var apiURL by mutableStateOf("http://crousapp-dev.nito4489.odns.fr/api/messages/unsent")
}