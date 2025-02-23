package com.example.sms_sender.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sms_sender.R
import com.example.sms_sender.ui.screen.home.HomeUiState

data class InfoData(
    val label: String = "",
    val value: Int = 0,
    var painter: Painter,
)


@Composable
fun InfoSection(
    homeUiState: HomeUiState
){
    val infos = listOf(
        InfoData(stringResource(R.string.message_total), homeUiState.totalSms, painterResource(R.drawable.message_icon)),
        InfoData(stringResource(R.string.message_sent), homeUiState.numOfSmsSent, painterResource(R.drawable.message_sent) ),
        InfoData(stringResource(R.string.message_unsent), homeUiState.numOfUnSmsSent, painterResource(R.drawable.message_unsent)),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp)

    ) {
        items(infos.size){ i ->
            InfoItem(infos[i])
        }
    }
}

@Composable
fun InfoItem(infoData: InfoData){
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = infoData.painter,
                    contentDescription = stringResource(R.string.setting),
                    modifier = Modifier.size(30.dp),
                )
                Text(
                    text = infoData.value.toString(),
                    fontSize = 19.sp
                )
            }
            Text(infoData.label)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun InfoSectionPreview(){
    InfoSection(HomeUiState(0, 0))
}

@Composable
@Preview(showBackground = true)
fun InfoItemPreview(){
    InfoItem(InfoData(stringResource(R.string.message_sent), 10, painterResource(R.drawable.message_icon)))
}