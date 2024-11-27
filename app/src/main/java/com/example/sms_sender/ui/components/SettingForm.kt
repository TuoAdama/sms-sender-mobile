import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.service.setting.SettingKey
import com.example.sms_sender.ui.components.CountryChoice
import com.example.sms_sender.ui.theme.SmssenderTheme
import com.example.sms_sender.viewmodel.SettingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingForm(initData: SettingViewModel = SettingViewModel(), onSubmit: (data: Map<String, String>) -> Unit = { data -> println(data.size)}) {

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
                        .padding(20.dp, 80.dp)
                ) {
                    Column {
                        Text(text = "Pays", modifier = Modifier.padding(0.dp, 10.dp))
                        CountryChoice{ value -> initData.country = value }

                        Spacer(Modifier.padding(0.dp, 10.dp))

                        Text("Api URL")
                        TextField(value = initData.apiURL, onValueChange = {value -> initData.apiURL = value})

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