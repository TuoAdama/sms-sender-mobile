package com.example.sms_sender.ui.screen.setting

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
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.service.setting.Setting
import kotlinx.coroutines.launch

class SettingViewModel(private val dataStoreService: DataStoreService) : ViewModel() {
    var settingUiState by mutableStateOf(SettingUiState())
        private set

    var settingErrorState by mutableStateOf(SettingErrorState())
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
            apiURL = dataStoreService.getString(Setting.API_URL_KEY) ?: "",
            country = dataStoreService.getString(Setting.COUNTRY_KEY) ?: "",
            isAuthenticated = dataStoreService.getBoolean(Setting.API_IS_AUTHENTICATED_KEY) ?: false,
            token = dataStoreService.getString(Setting.API_TOKEN_KEY) ?: "",
            isRunning = false,
            scheduleTime = dataStoreService.getInt(Setting.SCHEDULE_TIME_KEY) ?: Setting.SCHEDULE_TIME_DEFAULT_VALUE
        )
    }

    suspend fun update() {
        dataStoreService.saveBoolean(Setting.API_IS_AUTHENTICATED_KEY, settingUiState.isAuthenticated)
        dataStoreService.saveString(Setting.API_TOKEN_KEY, settingUiState.token)

        val url = settingUiState.apiURL;

        dataStoreService.saveString(Setting.API_URL_KEY, if( url.endsWith("/") ) url else url.plus("/") )
        dataStoreService.saveString(Setting.COUNTRY_KEY, settingUiState.country)
        dataStoreService.saveInt(Setting.SCHEDULE_TIME_KEY, settingUiState.scheduleTime)
    }


    fun validate(): Boolean {
        settingErrorState = SettingErrorState();
        return this.validateApiUrl() && this.validateToken()
    }


    private fun validateApiUrl(): Boolean{
        settingErrorState = settingErrorState.copy(apiUrlError = null)
        if (settingUiState.apiURL.isEmpty()){
            settingErrorState = settingErrorState.copy(apiUrlError = R.string.api_error)
        }
        if (!URLUtil.isValidUrl(settingUiState.apiURL)){
            settingErrorState = settingErrorState.copy(apiUrlError = R.string.api_url_not_valid)
        }
        return settingErrorState.apiUrlError === null
    }

    private fun validateToken(): Boolean {
        settingErrorState = settingErrorState.copy(tokenError = null)

        if (settingUiState.isAuthenticated && settingUiState.token.isEmpty()){
            settingErrorState = settingErrorState.copy(tokenError = R.string.token_not_empty)
        }
        return settingErrorState.tokenError === null
    }

}

data class SettingUiState (
    val isRunning: Boolean = false,
    val isLoading: Boolean = false,
    val scheduleTime: Int = Setting.SCHEDULE_TIME_DEFAULT_VALUE,
    val apiURL: String = "",
    val country: String = "",
    val isAuthenticated: Boolean = false,
    val authenticationHeader: String = "",
    val token: String = "",
)


data class SettingErrorState(
    val scheduleTime: Int = Setting.SCHEDULE_TIME_DEFAULT_VALUE,
    val apiUrlError: Int? = null,
    val authenticationHeaderError: Int? = null,
    val tokenError: Int? = null,
)