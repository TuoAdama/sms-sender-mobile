package com.example.sms_sender.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sms_sender.ui.screen.home.HomeUiState

@Composable
fun InfoSection(
    homeUiState: HomeUiState
){
    Column {
        InfoItem("Total sms", homeUiState.totalSms)
        InfoItem("sent sms", homeUiState.numOfSmsSent)
        InfoItem("unSent sms", homeUiState.numOfUnSmsSent)
    }
}

@Composable
fun InfoItem(title: String, value: Int){
    Text("$title:  $value")
}


@Composable
@Preview
fun InfoSectionPreview(){
    InfoSection(HomeUiState(0, 0))
}