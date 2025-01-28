import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.example.sms_sender.ui.theme.SmssenderTheme
import com.example.sms_sender.util.ColorUtils
import com.example.sms_sender.viewmodel.SettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingForm(
    initData: SettingViewModel = SettingViewModel(),
    onSubmit: (data: Map<String, Any>) -> Unit = { data -> println(data.size)},
    onStartService: () -> Unit = {},
    onStopService: () -> Unit = {}
) {
    val greenColor = Color(76, 175, 80, 255);
    val redColor = Color(233, 30, 99, 255)

    SmssenderTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(stringResource(R.string.setting))
                    }
                )
            },
            content = { padding ->

                if (initData.loading) {
                    Row(
                        modifier = Modifier.fillMaxHeight()
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.width(64.dp),
                            color = Color.Black,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }else {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp, 80.dp),
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(text = stringResource(R.string.form_title), modifier = Modifier.padding(0.dp, 10.dp),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                            val runningText = if(initData.isRunning) stringResource(R.string.service_running) else stringResource(R.string.service_stop)
                            Text(text = "($runningText)",
                                modifier = Modifier.padding(3.dp, 10.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if(initData.isRunning) greenColor else redColor
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Button(
                                onClick = onStartService,
                                colors = ButtonColors(ColorUtils.greenColors, Color.Black, ColorUtils.greenColorsDisabled, Color.Black),
                                enabled = !initData.isRunning
                            )
                            {
                                Text(stringResource(R.string.form_start_btn))
                            }
                            Button(
                                onClick = onStopService,
                                modifier = Modifier.padding(10.dp, 0.dp),
                                colors = ButtonColors(ColorUtils.redColors, Color.Black, ColorUtils.redColorsDisabled, Color.Black),
                                enabled = initData.isRunning
                            ) {
                                Text(stringResource(R.string.form_stop_btn))
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(PaddingValues(0.dp, 23.dp, 0.dp, 30.dp)))
                        Text(text = stringResource(R.string.form_config_name),
                            modifier = Modifier.padding(0.dp, 10.dp),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = stringResource(R.string.country), modifier = Modifier.padding(0.dp, 10.dp))
                        CountryChoice(modifier = Modifier.fillMaxWidth()){
                                value -> initData.country = value
                        }

                        Spacer(Modifier.padding(0.dp, 10.dp))

                    OutlinedTextField(
                        label = {
                            Text("API URL")
                        },
                        value = initData.apiURL,
                        onValueChange = {value -> initData.apiURL = value },
                    )

                        Spacer(Modifier.padding(0.dp, 10.dp))

                        Text(text = stringResource(R.string.authentication), modifier = Modifier.padding(0.dp, 10.dp))
                        Switch(
                            checked = initData.isAuthenticated,
                            onCheckedChange = {
                                initData.isAuthenticated = it
                            }
                        )

                        if (initData.isAuthenticated){
                            Spacer(Modifier.padding(0.dp, 10.dp))
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text(stringResource(R.string.form_auth_name))
                                },
                                value = initData.authenticationHeader,
                                onValueChange = {value -> initData.authenticationHeader = value },
                            )
                            Spacer(Modifier.padding(0.dp, 10.dp))
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                label = {
                                    Text(stringResource(R.string.form_header_name))
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                value = initData.token,
                                maxLines = 1,
                                onValueChange = {value -> initData.token = value },
                            )
                        }

                        Spacer(Modifier.padding(0.dp, 10.dp))

                        Button(onClick = {
                            val data = HashMap<String, Any>().apply{
                                set(SettingKey.API_URL_KEY, initData.apiURL)
                                set(SettingKey.COUNTRY_KEY, initData.country)
                                set(SettingKey.API_IS_AUTHENTICATED, initData.isAuthenticated)
                                set(SettingKey.API_AUTHORISATION_HEADER, initData.authenticationHeader)
                                set(SettingKey.API_TOKEN, initData.token)
                            }
                            onSubmit(data);
                        }) {
                            Text(stringResource(R.string.save_btn))
                        }
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SmssenderTheme {
        SettingForm()
    }
}