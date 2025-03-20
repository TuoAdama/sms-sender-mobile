package com.example.sms_sender.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sender.R
import com.example.sms_sender.smsServiceIsRunning
import com.example.sms_sender.startSmsService
import com.example.sms_sender.stopSmsService
import com.example.sms_sender.ui.components.EmptyMessageInfo
import com.example.sms_sender.ui.components.HomeTopBar
import com.example.sms_sender.ui.components.InfoSection
import com.example.sms_sender.ui.components.NetworkMessageError
import com.example.sms_sender.ui.components.messages.SmsMessageList
import com.example.sms_sender.ui.screen.setting.SettingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("MutableCollectionMutableState")
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
    val coroutineScope = rememberCoroutineScope();

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
                        canStartService = settingViewModel.isValid() && isNetworkConnect.value,
                    )
                },
            )
        },
        content = { padding ->

            Column(Modifier.padding(top = padding.calculateTopPadding())) {

                if (!isNetworkConnect.value){
                    NetworkMessageError(
                        modifier = Modifier.padding(10.dp, 20.dp)
                    )
                }

                InfoSection(homeUiState)

                Text(
                    modifier = Modifier.padding(start = 15.dp, top = 15.dp, bottom = 10.dp),
                    fontSize = 25.sp,
                    text = stringResource(R.string.messages)
                )

                HorizontalDivider(Modifier.padding(10.dp))

                if (homeUiState.messages.isNotEmpty()){
                    SmsMessageList(
                        modifier = Modifier.padding(start = 15.dp),
                        messages = homeUiState.messages,
                        onDelete = {smsData ->
                            coroutineScope.launch(Dispatchers.Default) {
                                homeViewModel.delete(smsData)
                            }
                        }
                    )
                }else{
                    EmptyMessageInfo(
                        modifier = Modifier.fillMaxWidth()
                                    .fillMaxHeight()
                    )
                }
            }
        }
    )
}