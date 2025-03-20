package com.example.sms_sender.ui.screen.sms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.sms_sender.App
import com.example.sms_sender.data.repository.SmsDataRepository
import com.example.sms_sender.model.SmsData
import com.example.sms_sender.network.SmsResponse
import java.time.LocalDateTime

class SmsViewModel(val repository: SmsDataRepository) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appContainer = (this[APPLICATION_KEY] as App).appContainer
                SmsViewModel(appContainer.smsDataRepository)
            }
        }
    }

    suspend fun insertItemFromSmsResponse(smsResponse: SmsResponse){
        val smsData = SmsData(recipient = smsResponse.recipient, message = smsResponse.message)
        repository.insert(smsData)
    }

}