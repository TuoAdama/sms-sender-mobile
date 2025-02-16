package com.example.sms_sender.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sender.R
import com.example.sms_sender.smsServiceIsRunning
import com.example.sms_sender.startSmsService
import com.example.sms_sender.stopSmsService
import com.example.sms_sender.ui.components.HomeTopBar
import com.example.sms_sender.ui.components.InfoSection
import com.example.sms_sender.ui.components.SmsMessageList
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
    val homeUiState by homeViewModel.homeUiState.collectAsState()
    val context = LocalContext.current
    val isNetworkConnect = homeViewModel.isNetworkConnected.collectAsState()

    var isServiceRunning by remember {
        mutableStateOf(context.smsServiceIsRunning())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    HomeTopBar(
                        onClickSetting = navigateToSettingScreen,
                        onStartService = {
                            context.startSmsService(settingViewModel.getSetting())
                            isServiceRunning = context.smsServiceIsRunning();
                        },
                        onStopService = {
                            context.stopSmsService();
                            isServiceRunning = context.smsServiceIsRunning();
                        },
                        isServiceRunning =isServiceRunning,
                        canStartService = settingViewModel.isSettingValid() && isNetworkConnect.value,
                    )
                },
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(20.dp, padding.calculateTopPadding())) {
                if (!isNetworkConnect.value){
                    NetworkMessageError(
                        modifier = Modifier.padding(0.dp, 20.dp)
                    )
                }
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
fun NetworkMessageError(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(Color.Red.copy(alpha = 0.65f)).fillMaxWidth()
            .padding(20.dp, 15.dp)
    ) {
        Text(
            stringResource(R.string.not_error),
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
@Preview(showBackground = true)
fun NetworkMessageErrorPreview(){
    NetworkMessageError()
}