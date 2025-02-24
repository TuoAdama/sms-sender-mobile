package com.example.sms_sender.ui.screen.setting

import android.util.Log
import android.webkit.URLUtil
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
import com.example.sms_sender.R
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
        if (isValid()){
            viewModelScope.launch {
                dataStoreService.saveBoolean(SmsService.API_IS_AUTHENTICATED, settingUiState.isAuthenticated)
                dataStoreService.saveString(SmsService.API_TOKEN, settingUiState.token)
                dataStoreService.saveString(SmsService.API_URL_KEY, settingUiState.apiURL)
                dataStoreService.saveString(SmsService.COUNTRY_KEY, settingUiState.country)
            }
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


    fun isValid(): Boolean {
        return this.validateApiUrl() && this.validateToken()
    }


    private fun validateApiUrl(): Boolean{
        settingUiState = settingUiState.copy(apiUrlError = null)
        if (settingUiState.apiURL.isEmpty()){
            settingUiState = settingUiState.copy(apiUrlError = R.string.api_error)
        }
        if (!URLUtil.isValidUrl(settingUiState.apiURL)){
            settingUiState = settingUiState.copy(apiUrlError = R.string.api_url_not_valid)
        }
        return settingUiState.apiUrlError === null
    }

    private fun validateToken(): Boolean {
        settingUiState = settingUiState.copy(tokenError = null)

        if (settingUiState.isAuthenticated && settingUiState.token.isEmpty()){
            settingUiState = settingUiState.copy(tokenError = R.string.token_not_empty)
        }
        return settingUiState.tokenError === null
    }

}

data class SettingUiState (
    val isRunning: Boolean = false,
    val isLoading: Boolean = false,

    val apiURL: String = "",
    val apiUrlError: Int? = null,

    val country: String = "",
    val isAuthenticated: Boolean = false,

    val authenticationHeader: String = "",
    val authenticationHeaderError: Int? = null,

    val token: String = "",
    val tokenError: Int? = null,
)