package com.example.sms_sender.ui.screen.setting

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sms_sender.App
import com.example.sms_sender.model.Setting
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.service.SmsService
import kotlinx.coroutines.launch

class SettingViewModel(private val dataStoreService: DataStoreService) : ViewModel() {
    var settingUiState by mutableStateOf(SettingUiState())
        private set

    init {
        viewModelScope.launch {
            initWithPreferenceData()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as App)
                SettingViewModel(dataStoreService = DataStoreService(application))
            }
        }
    }

    fun updateSetting(settingUiState: SettingUiState){
        this.settingUiState = settingUiState;
    }

    fun isSettingValid(): Boolean{
        if (settingUiState.isAuthenticated && settingUiState.token.isEmpty()){
            return false;
        }
        Log.i("setting-valid", "${settingUiState.apiURL.isNotEmpty()} and ${settingUiState.country}")
        return settingUiState.apiURL.isNotEmpty() && settingUiState.country.isNotEmpty()
    }

    private suspend fun initWithPreferenceData(){
        settingUiState = SettingUiState(
            apiURL = dataStoreService.getString(SmsService.API_URL_KEY) ?: "",
            country = dataStoreService.getString(SmsService.COUNTRY_KEY) ?: "",
            isAuthenticated = dataStoreService.getBoolean(SmsService.API_IS_AUTHENTICATED) ?: false,
            token = dataStoreService.getString(SmsService.API_TOKEN) ?: "",
            isRunning = false
        )
    }

    fun update() {
        viewModelScope.launch {
            dataStoreService.saveBoolean(SmsService.API_IS_AUTHENTICATED, settingUiState.isAuthenticated)
            dataStoreService.saveString(SmsService.API_TOKEN, settingUiState.token)
            dataStoreService.saveString(SmsService.API_URL_KEY, settingUiState.apiURL)
            dataStoreService.saveString(SmsService.COUNTRY_KEY, settingUiState.country)
        }
    }

    fun getSetting(): Setting {
        return Setting(
            country = settingUiState.country,
            isAuthenticated = settingUiState.isAuthenticated,
            token = settingUiState.token,
            domain = settingUiState.apiURL,
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