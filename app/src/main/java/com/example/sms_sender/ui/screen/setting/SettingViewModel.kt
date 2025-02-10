package com.example.sms_sender.ui.screen.setting

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
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.service.setting.SettingKey
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
                SettingViewModel(DataStoreService(application.applicationContext))
            }
        }
    }

    fun updateSetting(settingUiState: SettingUiState){
        this.settingUiState = settingUiState;
    }

    fun setRunning(isRunning: Boolean){
        this.settingUiState = settingUiState.copy(isRunning = isRunning)
    }

    private suspend fun initWithPreferenceData(){
        settingUiState = SettingUiState(
            apiURL = dataStoreService.getString(SettingKey.API_URL_KEY) ?: "",
            country = dataStoreService.getString(SettingKey.COUNTRY_KEY) ?: "",
            isAuthenticated = dataStoreService.getBoolean(SettingKey.API_IS_AUTHENTICATED) ?: false,
            token = dataStoreService.getString(SettingKey.API_TOKEN) ?: "",
            authenticationHeader = dataStoreService.getString(SettingKey.API_AUTHORISATION_HEADER) ?: "",
            isRunning = false
        )
    }

    fun update(formData: Map<String, Any>) {
        formData.forEach {(key, value) ->
            viewModelScope.launch {
                when (value) {
                    is Boolean -> dataStoreService.saveBoolean(key, value)
                    is Int -> dataStoreService.saveInt(key, value)
                    is String -> dataStoreService.saveString(key, value)
                }
            }
        }
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