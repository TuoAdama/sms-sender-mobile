import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sms_sender.service.setting.SettingKey
import com.example.sms_sender.ui.components.CountryChoice
import com.example.sms_sender.ui.theme.SmssenderTheme
import com.example.sms_sender.util.ColorUtils
import com.example.sms_sender.viewmodel.SettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingForm(
    initData: SettingViewModel = SettingViewModel(),
    onSubmit: (data: Map<String, String>) -> Unit = { data -> println(data.size)},
    onStartService: () -> Unit = {},
    onStopService: () -> Unit = {}
) {
    val greenColor = Color(76, 175, 80, 255);
    val redColor = Color(233, 30, 99, 255)

    var checked by remember { mutableStateOf(false) }

    SmssenderTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Paramètre")
                    }
                )
            },
            content = { padding ->
                Column (
                    modifier = Modifier.fillMaxWidth()
                        .padding(20.dp, 80.dp),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Sms Service", modifier = Modifier.padding(0.dp, 10.dp),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                        val runningText = if(initData.isRunning) "running" else "stop"
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
                            Text("Start")
                        }
                        Button(
                            onClick = onStopService,
                            modifier = Modifier.padding(10.dp, 0.dp),
                            colors = ButtonColors(ColorUtils.redColors, Color.Black, ColorUtils.redColorsDisabled, Color.Black),
                            enabled = initData.isRunning
                        ) {
                            Text("Stop")
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(PaddingValues(0.dp, 23.dp, 0.dp, 30.dp)))
                    Text(text = "Configuration",
                        modifier = Modifier.padding(0.dp, 10.dp),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Pays", modifier = Modifier.padding(0.dp, 10.dp))
                    CountryChoice(modifier = Modifier.fillMaxWidth()){
                            value -> initData.country = value
                    }

                    Spacer(Modifier.padding(0.dp, 10.dp))

                    //TextField(value = initData.apiURL, onValueChange = {value -> initData.apiURL = value})
                    OutlinedTextField(
                        label = {
                            Text("API URL")
                        },
                        value = initData.apiURL,
                        onValueChange = {value -> initData.apiURL = value },
                    )

                    Spacer(Modifier.padding(0.dp, 10.dp))

                    Text(text = "Authenfication ?", modifier = Modifier.padding(0.dp, 10.dp))
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        }
                    )

                    if (checked){
                        Spacer(Modifier.padding(0.dp, 10.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text("Authenfication header")
                            },
                            value = "Authorization",
                            onValueChange = {value -> initData.apiURL = value },
                        )
                        Spacer(Modifier.padding(0.dp, 10.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            label = {
                                Text("Header value")
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            value = initData.apiURL,
                            onValueChange = {value -> initData.apiURL = value },
                        )
                    }

                    Spacer(Modifier.padding(0.dp, 10.dp))

                    Button(onClick = {
                        val data = HashMap<String, String>().apply{
                            set(SettingKey.API_URL_KEY, initData.apiURL)
                            set(SettingKey.COUNTRY_KEY, initData.country)
                        }
                        onSubmit(data);
                    }) {
                        Text("Enregistrer")
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