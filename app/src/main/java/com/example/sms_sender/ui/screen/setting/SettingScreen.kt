package com.example.sms_sender.ui.screen.setting

import SettingForm
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.ui.components.LoadingComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel,
    navigateBack: () -> Unit = {}
) {
    
    val settingUiState = settingViewModel.settingUiState;
    
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.setting)) },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
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