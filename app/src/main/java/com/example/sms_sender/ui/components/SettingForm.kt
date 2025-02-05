import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sms_sender.R
import com.example.sms_sender.service.setting.SettingKey
import com.example.sms_sender.ui.components.CountryChoice
import com.example.sms_sender.ui.screen.SettingUiState
import com.example.sms_sender.ui.screen.SettingViewModel
import com.example.sms_sender.ui.theme.SmssenderTheme
import com.example.sms_sender.util.ColorUtils

@Composable
fun SettingForm(
    settingViewModel: SettingViewModel,
    onSubmit: (data: Map<String, Any>) -> Unit = { data -> println(data.size)},
) {

    val settingUiState: SettingUiState = settingViewModel.settingUiState

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
                Text("API URL")
            },
            modifier = Modifier.fillMaxWidth(),
            value = settingUiState.apiURL,
            onValueChange = {value -> settingViewModel.updateSetting(
                settingUiState.copy(apiURL = value)
            ) },
        )

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
                value = settingUiState.authenticationHeader,
                onValueChange = {
                    value -> settingViewModel.updateSetting(
                    settingUiState.copy(
                    authenticationHeader = value
                )) },
            )
            Spacer(Modifier.padding(0.dp, 10.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(stringResource(R.string.form_header_name))
                },
                visualTransformation = PasswordVisualTransformation(),
                value = settingUiState.token,
                maxLines = 1,
                onValueChange = {value ->
                    settingViewModel.updateSetting(
                        settingUiState.copy(token = value)
                    )
                },
            )
        }

        Spacer(Modifier.padding(0.dp, 10.dp))

        Button(onClick = {
            val data = HashMap<String, Any>().apply{
                set(SettingKey.API_URL_KEY, settingUiState.apiURL)
                set(SettingKey.COUNTRY_KEY, settingUiState.country)
                set(SettingKey.API_IS_AUTHENTICATED, settingUiState.isAuthenticated)
                set(SettingKey.API_AUTHORISATION_HEADER, settingUiState.authenticationHeader)
                set(SettingKey.API_TOKEN, settingUiState.token)
            }
            onSubmit(data);
        }) {
            Text(stringResource(R.string.save_btn))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmssenderTheme {
        SettingForm(
            settingViewModel = SettingViewModel()
        )
    }
}