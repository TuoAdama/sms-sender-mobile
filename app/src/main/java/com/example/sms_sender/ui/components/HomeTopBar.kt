package com.example.sms_sender.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onClickSetting: () -> Unit = {}
){
    Row(
        modifier = modifier
                .fillMaxWidth()
            .padding(0.dp, 0.dp, 15.dp, 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.messages))
        IconButton(onClick = onClickSetting) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = stringResource(R.string.setting),
                modifier = Modifier.size(35.dp),
            )
        }
    }
}


@Composable
@Preview
fun HomeTopBarPreview(){
    HomeTopBar()
}