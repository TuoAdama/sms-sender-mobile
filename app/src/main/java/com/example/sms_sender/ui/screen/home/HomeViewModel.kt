package com.example.sms_sender.ui.screen.home

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
import com.example.sms_sender.data.repository.SmsDataRepository
import com.example.sms_sender.model.SmsData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(private val smsDataRepository: SmsDataRepository) : ViewModel() {

    var homeUiState by mutableStateOf(HomeUiState())
    private set


    init {
        viewModelScope.launch {
            homeUiState =  HomeUiState(
                numOfSmsSent = smsDataRepository.getCountSmsSent().first(),
                numOfUnSmsSent = smsDataRepository.getCountUnSmsSent().first(),
                totalSms = smsDataRepository.count().first(),
                messages = smsDataRepository.getItems().first()
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as App).appContainer
                HomeViewModel(appContainer.smsDataRepository)
            }
        }
    }

    fun setIsServiceRunning(isRunning: Boolean){
        this.homeUiState = this.homeUiState.copy(isSmsServiceRunning = isRunning)
    }
}


data class HomeUiState(
    val numOfSmsSent: Int = 0,
    val numOfUnSmsSent: Int = 0,
    val totalSms: Int = 0,
    val isSmsServiceRunning: Boolean = false,
    val messages: List<SmsData> = listOf(),
)