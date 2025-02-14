package com.example.sms_sender.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sender.R
import com.example.sms_sender.startSmsService
import com.example.sms_sender.stopSmsService
import com.example.sms_sender.ui.components.HomeTopBar
import com.example.sms_sender.ui.components.InfoSection
import com.example.sms_sender.ui.components.SmsMessageList
import com.example.sms_sender.ui.components.SmsServiceAction
import com.example.sms_sender.ui.navigation.NavigationRoute
import com.example.sms_sender.ui.screen.setting.SettingViewModel


object HomeScreenDestination : NavigationRoute {
    override var route = "home"
    override var titleRes = R.string.app_name
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToSettingScreen: () -> Unit = {},
    homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
    settingViewModel: SettingViewModel
){
    val homeUiState = homeViewModel.homeUiState

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    HomeTopBar(
                        onClickSetting = navigateToSettingScreen,
                        onStartService = {
                            homeViewModel.setIsServiceRunning(true)
                            context.stopSmsService();
                        },
                        onStopService = {
                            homeViewModel.setIsServiceRunning(false)
                            context.startSmsService(settingViewModel.getSetting())
                        },
                        isServiceRunning = homeUiState.isSmsServiceRunning,
                        isSettingValid = settingViewModel.isSettingValid(),
                    )
                },
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(20.dp, padding.calculateTopPadding())) {
                Text("is Auth: ${settingViewModel.settingUiState.isAuthenticated}")
                InfoSection(homeUiState)
                Text(
                    modifier = Modifier.padding(0.dp, 18.dp),
                    fontSize = 25.sp,
                    text = stringResource(R.string.messages)
                )
                SmsMessageList(homeUiState.messages)
            }
        }
    )
}


@Composable
@Preview
fun HomeScreenPreview(){

}