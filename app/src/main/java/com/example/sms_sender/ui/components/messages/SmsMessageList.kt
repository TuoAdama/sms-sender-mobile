package com.example.sms_sender.ui.components.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sms_sender.R
import com.example.sms_sender.model.SmsData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmsMessageList(
    modifier: Modifier = Modifier,
    messages: List<SmsData>,
    onDelete: (smsData: SmsData) -> Unit
){
    LazyColumn {
        itemsIndexed(
            items = messages,
            key = {_, listItem -> listItem.hashCode()}
        ){ _, item ->

            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (it == SwipeToDismissBoxValue.EndToStart) {
                        onDelete(item)
                        return@rememberSwipeToDismissBoxState true
                    }
                    return@rememberSwipeToDismissBoxState false
                },
                positionalThreshold = { it * .50f }
            )

            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    val color = when (dismissState.dismissDirection) {
                        SwipeToDismissBoxValue.EndToStart -> Color.Red
                        else -> Color.Transparent
                    }
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 16.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(
                            R.string.trash_description))
                    }
                },
            ) {
                MessageItem(item)
            }

            HorizontalDivider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmsMessageListPreview(

){
    SmsMessageList(
        modifier = Modifier,
        messages = mutableListOf(),
        onDelete = {}
    )
}