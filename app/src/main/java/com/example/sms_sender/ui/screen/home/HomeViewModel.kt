package com.example.sms_sender.ui.screen.home

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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val smsDataRepository: SmsDataRepository) : ViewModel() {

    private val _homeUiStateFlow =  MutableStateFlow(HomeUiState())
    var homeUiState: StateFlow<HomeUiState> = _homeUiStateFlow
    private set


    init {
        viewModelScope.launch {
            homeUiState = smsDataRepository.getItems().map {
                HomeUiState(
                    numOfSmsSent = smsDataRepository.getCountSmsSent().first(),
                    numOfUnSmsSent = smsDataRepository.getCountUnSmsSent().first(),
                    totalSms = smsDataRepository.count().first(),
                    messages = it
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = HomeUiState()
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
    val isSmsServiceRunning: Boolean = false,
    val messages: List<SmsData> = listOf(),
)