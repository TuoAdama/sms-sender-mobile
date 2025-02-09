package com.example.sms_sender.ui.screen.setting

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.service.setting.SettingKey

class SettingViewModel : ViewModel() {
    var settingUiState by mutableStateOf(SettingUiState())
        private set

    fun updateSetting(settingUiState: SettingUiState){
        this.settingUiState = settingUiState;
    }

    fun setRunning(isRunning: Boolean){
        this.settingUiState = settingUiState.copy(isRunning = isRunning)
    }

    suspend fun initWithPreferenceData(dataStore: DataStoreService){
        settingUiState = SettingUiState(
            apiURL = dataStore.getString(SettingKey.API_URL_KEY) ?: "",
            country = dataStore.getString(SettingKey.COUNTRY_KEY) ?: "",
            isAuthenticated = dataStore.getBoolean(SettingKey.API_IS_AUTHENTICATED) ?: false,
            token = dataStore.getString(SettingKey.API_TOKEN) ?: "",
            authenticationHeader = dataStore.getString(SettingKey.API_AUTHORISATION_HEADER) ?: "",
            isRunning = false
        )
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