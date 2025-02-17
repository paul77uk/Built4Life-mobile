package com.example.built4life.customcomposables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissibleItem(
    onDeleted: () -> Unit,
    modifier: Modifier = Modifier,
//    deleteComposable: @Composable () -> Unit,
    content: @Composable () -> Unit,
//    onDismiss: () -> Unit,
//    showDialog: Boolean = false
) {
    var showDialog by remember { mutableStateOf(false) }
    var dismissItem by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { swipeToDismissBoxValue ->
            when (swipeToDismissBoxValue) {
                SwipeToDismissBoxValue.StartToEnd -> {}
                SwipeToDismissBoxValue.EndToStart -> {
                    showDialog = true
                    return@rememberSwipeToDismissBoxState false
                }

                SwipeToDismissBoxValue.Settled -> {
                    return@rememberSwipeToDismissBoxState false
                }
            }
            return@rememberSwipeToDismissBoxState true
        },
        positionalThreshold = {
            it * 0.65f
        }
    )

    if (showDialog) {
        BasicAlertDialog(
            onDismissRequest = {
                showDialog = false
                dismissItem = false
            },
            modifier = modifier
                .clip(shape = RoundedCornerShape(5))
                .background(Color.White)
                .padding(16.dp),
        ) {
            Column {
                Text("Are you sure you want to delete this program?")
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Button(
                        onClick = {
                            showDialog = false
                            dismissItem = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray,
                        ),
                        shape = RoundedCornerShape(15),
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(
                        onClick = {
                            showDialog = false
                            dismissItem = true
                            onDeleted()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFE9900),
                        ),
                        shape = RoundedCornerShape(15),
                    ) {
                        Text("Delete")
                    }
                }
            }
        }
    }
    if (dismissItem) {
        LaunchedEffect(Unit) {
            dismissState.dismiss(SwipeToDismissBoxValue.EndToStart)
        }
    }

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier.animateContentSize(),
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            val backgroundColor by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.StartToEnd -> Color.Transparent
                    SwipeToDismissBoxValue.EndToStart -> Color.Red
                    SwipeToDismissBoxValue.Settled -> Color.LightGray
                }, label = ""
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .background(backgroundColor),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    Icons.Default.Delete, contentDescription = null, tint = Color.White,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
        }
    ) {
        content()
    }
}