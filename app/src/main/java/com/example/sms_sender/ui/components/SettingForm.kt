import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sms_sender.R
import com.example.sms_sender.restartSmsService
import com.example.sms_sender.service.DataStoreService
import com.example.sms_sender.ui.components.dropdown.CountryChoice
import com.example.sms_sender.ui.components.dropdown.ScheduleSelect
import com.example.sms_sender.ui.screen.setting.SettingErrorState
import com.example.sms_sender.ui.screen.setting.SettingUiState
import com.example.sms_sender.ui.screen.setting.SettingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SettingForm(
    settingViewModel: SettingViewModel
) {

    val settingUiState: SettingUiState = settingViewModel.settingUiState
    val settingErrorState: SettingErrorState = settingViewModel.settingErrorState
    var tokenVisible by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.form_config_name),
            modifier = Modifier.padding(0.dp, 10.dp),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = stringResource(R.string.country), modifier = Modifier.padding(0.dp, 10.dp))
        CountryChoice(modifier = Modifier.fillMaxWidth()){
                value -> settingViewModel.updateSetting(
                        settingUiState.copy(country = value)
                )
        }

        Spacer(Modifier.padding(0.dp, 10.dp))

        OutlinedTextField(
            label = {
                Text(stringResource(R.string.setting_baseUrl))
            },
            modifier = Modifier.fillMaxWidth(),
            value = settingUiState.apiURL,
            onValueChange = {value -> settingViewModel.updateSetting(
                settingUiState.copy(apiURL = value)
            ) },
            isError = settingErrorState.apiUrlError !== null,
            supportingText = {
                if (settingErrorState.apiUrlError !== null){
                    Text(stringResource(settingErrorState.apiUrlError))
                }
            }
        )

        ScheduleSelect(modifier = Modifier.fillMaxWidth(), value = settingUiState.scheduleTime){
            settingViewModel.updateSetting(
                settingUiState.copy(
                    scheduleTime = it
                )
            )
        }

        Spacer(Modifier.padding(0.dp, 10.dp))

        Text(text = stringResource(R.string.authentication), modifier = Modifier.padding(0.dp, 10.dp))
        Switch(
            checked = settingUiState.isAuthenticated,
            onCheckedChange = {
                    value -> settingViewModel.updateSetting(
                            settingUiState.copy(isAuthenticated = value)
                    )
            }
        )

        if (settingUiState.isAuthenticated){
            Spacer(Modifier.padding(0.dp, 10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(R.string.form_auth_name))
                },
                readOnly = true,
                value = stringResource(R.string.authorizationHeader),
                onValueChange = {}
            )
            Spacer(Modifier.padding(0.dp, 10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(R.string.form_header_name))
                },
                visualTransformation = if (tokenVisible) VisualTransformation.None else PasswordVisualTransformation(),
                value = settingUiState.token,
                maxLines = 1,
                isError = settingErrorState.tokenError !== null,
                trailingIcon = {
                    IconButton(onClick = {tokenVisible = !tokenVisible}){
                        Icon(imageVector = if (tokenVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility, stringResource(R.string.visibility))
                    }
                },
                onValueChange = {value ->
                    settingViewModel.updateSetting(
                        settingUiState.copy(token = value)
                    )
                },
                supportingText = {
                    if (settingErrorState.tokenError !== null ){
                        Text(stringResource(settingErrorState.tokenError))
                    }
                }
            )
        }

        Spacer(Modifier.padding(0.dp, 10.dp))

        Button(
            onClick = {

            val isValid = settingViewModel.validate()

            if (isValid){
                settingViewModel.updateSetting(
                    settingUiState.copy(isLoading = true)
                )
                CoroutineScope(Dispatchers.Default).launch {
                    settingViewModel.update()
                    context.restartSmsService(settingUiState)
                    settingViewModel.updateSetting(
                        settingUiState.copy(isLoading = false)
                    )
                }

                Toast.makeText(context, R.string.setting_saved, Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(stringResource(R.string.save_btn))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SettingForm(
        settingViewModel = SettingViewModel(DataStoreService(LocalContext.current))
    )
}