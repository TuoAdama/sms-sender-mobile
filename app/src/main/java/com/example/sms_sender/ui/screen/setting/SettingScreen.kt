package com.example.sms_sender.ui.screen.setting

import SettingForm
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.ui.components.LoadingComponent
import com.example.sms_sender.ui.navigation.NavigationRoute


object SettingScreenDestination : NavigationRoute {
    override var route = "setting"
    override var titleRes = R.string.setting
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel,
) {
    
    val settingUiState = settingViewModel.settingUiState;
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.setting))
                }
            )
        },
        content = { padding ->

            var modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(20.dp, padding.calculateTopPadding())

            if (!settingUiState.isLoading){
                modifier = modifier.verticalScroll(rememberScrollState())
            }

            Column (
                modifier = modifier,
            ){
                if (settingUiState.isLoading) {
                    LoadingComponent()
                }else {
                    HorizontalDivider(modifier = Modifier.padding(PaddingValues(0.dp, 23.dp, 0.dp, 30.dp)))

                    SettingForm(settingViewModel = settingViewModel)
                }
            }
        }
    )
}


@Composable
@Preview(showBackground = true)
fun SettingPreview(){
    SettingScreen(
        settingViewModel = SettingViewModel(DataStoreService(LocalContext.current))
    )
}