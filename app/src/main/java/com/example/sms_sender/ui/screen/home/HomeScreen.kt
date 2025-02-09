package com.example.sms_sender.ui.screen.home


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sender.R
import com.example.sms_sender.ui.components.InfoSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
){

    val homeUiState = homeViewModel.homeUiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.setting))
                }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                InfoSection(homeUiState)
            }
        }
    )
}