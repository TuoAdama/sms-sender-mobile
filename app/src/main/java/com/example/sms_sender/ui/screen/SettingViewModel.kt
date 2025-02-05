package com.example.sms_sender.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingViewModel : ViewModel() {
    var settingUiState by mutableStateOf(SettingUiState())
        private set

    fun updateSetting(settingUiState: SettingUiState){
        this.settingUiState = settingUiState;
    }

}

data class SettingUiState (
    val isRunning: Boolean = false,
    val isLoading: Boolean = false,
    val apiURL: String = "",
    val country: String = "",
    val isAuthenticated: Boolean = false,
    val authenticationHeader: String = "",
    val token: String = "",
)