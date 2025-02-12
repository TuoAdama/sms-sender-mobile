package com.example.sms_sender.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sms_sender.R
import com.example.sms_sender.model.Setting
import com.example.sms_sender.smsServiceIsRunning
import com.example.sms_sender.startSmsService
import com.example.sms_sender.stopSmsService
import com.example.sms_sender.ui.screen.setting.SettingViewModel
import com.example.sms_sender.util.ColorUtils
import kotlin.random.Random

@Composable
fun SmsServiceAction (
    settingViewModel: SettingViewModel = viewModel(factory = SettingViewModel.Factory)
){

    val context = LocalContext.current

    val isServiceRunning = remember {
        mutableStateOf(context.smsServiceIsRunning())
    }

    val greenColor = Color(76, 175, 80, 255);
    val redColor = Color(233, 30, 99, 255)

    Column{
        Text("is running: ${isServiceRunning.value}, ${Random.nextInt()}")
        Text("is valid: ${settingViewModel.isSettingValid()}")
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
        ){
            Text(text = stringResource(R.string.form_title), modifier = Modifier.padding(0.dp, 10.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            val runningText = if(isServiceRunning.value) stringResource(R.string.service_running) else stringResource(R.string.service_stop)

            Text(text = "($runningText)",
                modifier = Modifier.padding(3.dp, 10.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if(isServiceRunning.value) greenColor else redColor
            )
        }

        Row(
            modifier = Modifier.padding(0.dp, 5.dp)
        ) {
            Button(
                onClick = {
                    context.startSmsService(settingViewModel.getSetting())
                    isServiceRunning.value = true
                },
                colors = ButtonColors(ColorUtils.greenColors, Color.Black, ColorUtils.greenColorsDisabled, Color.Black),
                enabled = !isServiceRunning.value && settingViewModel.isSettingValid()
            )
            {
                Text(stringResource(R.string.form_start_btn))
            }
            Button(
                onClick = {
                    context.stopSmsService()
                    isServiceRunning.value = false
                },
                modifier = Modifier.padding(10.dp, 0.dp),
                colors = ButtonColors(ColorUtils.redColors, Color.Black, ColorUtils.redColorsDisabled, Color.Black),
                enabled = isServiceRunning.value && settingViewModel.isSettingValid()
            ) {
                Text(stringResource(R.string.form_stop_btn))
            }
        }

        if (settingViewModel.isSettingValid()){
            Text(stringResource(R.string.setting_required))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmsServiceActionPreview(){
    SmsServiceAction()
}