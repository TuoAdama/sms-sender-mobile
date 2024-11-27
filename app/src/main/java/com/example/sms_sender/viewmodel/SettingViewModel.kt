package com.example.sms_sender.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {
    var country by mutableStateOf("")
    var apiURL by mutableStateOf("")
}