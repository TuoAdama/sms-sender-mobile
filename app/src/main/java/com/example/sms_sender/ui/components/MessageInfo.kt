package com.example.sms_sender.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R

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
fun EmptyMessageInfo(modifier: Modifier = Modifier){
    Box(
        modifier = modifier.wrapContentSize(Alignment.Center)
    ){
        Text(stringResource(R.string.messages_not_exist))
    }
}


@Composable
@Preview(showBackground = true)
fun EmptyMessageInfoPreview(){
    EmptyMessageInfo()
}


@Composable
@Preview(showBackground = true)
fun NetworkMessageErrorPreview(){
    NetworkMessageError()
}