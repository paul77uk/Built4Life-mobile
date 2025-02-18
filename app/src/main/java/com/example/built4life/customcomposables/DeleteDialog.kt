package com.example.built4life.customcomposables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteDialog(
    onDismissRequest: () -> Unit, modifier: Modifier = Modifier, onDelete: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
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
                        onDismissRequest()
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
                        onDismissRequest()
                        onDelete()
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