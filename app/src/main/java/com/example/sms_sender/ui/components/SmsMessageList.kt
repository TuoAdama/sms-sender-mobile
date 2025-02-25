package com.example.sms_sender.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.SwipeToDismissBoxValue.StartToEnd
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R
import com.example.sms_sender.model.SmsData

@Composable
fun SmsMessageList(
    modifier: Modifier = Modifier,
    messages: List<SmsData>,
){
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        if (messages.isNotEmpty()){
            messages.forEach {
                SmsMessageItemSwipe(smsData = it, modifier = Modifier.padding(0.dp, 10.dp).fillMaxWidth())
                HorizontalDivider()
            }
        }else {
            Text(stringResource(R.string.noMessage))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsMessageItemSwipe(modifier: Modifier = Modifier, smsData: SmsData){
    val context = LocalContext.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when(it) {
                StartToEnd -> {
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    Toast.makeText(context, "Item archived", Toast.LENGTH_SHORT).show()
                }
                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
        // positional threshold of 25%
        positionalThreshold = { it * .25f }
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = { DismissBackground(dismissState)},
        content = {
            MessageItem(smsData)
        })
}

@Composable
fun MessageItem(smsData: SmsData){
    ListItem(
        modifier = Modifier.clip(MaterialTheme.shapes.small),
        headlineContent = {
            Text(
                smsData.recipient,
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(
                smsData.message,
                style = MaterialTheme.typography.bodySmall
            )
        },
        leadingContent = {
            Icon(
                imageVector = Icons.Filled.Sms,
                contentDescription = "person icon",
                Modifier
                    .clip(CircleShape)
                    .padding(10.dp)
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
fun MessageItemPreview(){
    MessageItem(SmsData(1, "0751097177", "Bonjour"))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        StartToEnd -> Color.Transparent
        SwipeToDismissBoxValue.EndToStart -> Color.Red
        SwipeToDismissBoxValue.Settled -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "Archive"
        )
    }
}