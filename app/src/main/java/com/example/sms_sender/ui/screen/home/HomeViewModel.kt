package com.example.sms_sender.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sms_sender.App
import com.example.sms_sender.data.repository.SmsDataRepository
import com.example.sms_sender.model.SmsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(private val smsDataRepository: SmsDataRepository) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())

    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        viewModelScope.launch {
            _homeUiState.value =  HomeUiState(
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
}


data class HomeUiState(
    val numOfSmsSent: Int = 0,
    val numOfUnSmsSent: Int = 0,
    val totalSms: Int = 0,
    val messages: List<SmsData> = listOf(),
)