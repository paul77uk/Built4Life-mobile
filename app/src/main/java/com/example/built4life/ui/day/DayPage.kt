package com.example.built4life.ui.day

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.built4life.customcomposables.AppBar
import com.example.built4life.data.entities.Set
import kotlinx.coroutines.launch

@Composable
fun DayPage(
    modifier: Modifier = Modifier, navigateUp: () -> Unit, viewModel: DayViewModel = hiltViewModel()
) {
    val title = viewModel.dayRoute.title
    val programWithDays = viewModel.programWithDays.collectAsState(initial = emptyList())
    var dayWithExercisesAndSets =
        viewModel.dayWithExercisesAndSets.collectAsState(initial = emptyList())
    var chosenDay by remember { mutableIntStateOf(0) }

    Scaffold(topBar = {
        AppBar(
            modifier = modifier, title = title, canNavigateBack = true, navigateUp = navigateUp
        )
    }, bottomBar = {
        NavigationBar {
            programWithDays.value.map {
                it.days.forEachIndexed { index, day ->
                    NavigationBarItem(selected = index == chosenDay,
//                        enabled = index != chosenDay,
                        onClick = {
                            chosenDay = index
                            viewModel.getSets(day.dayId)
                        }, label = {
                            Text(text = day.title)
                        }, alwaysShowLabel = true, icon = {
                            Icon(
                                Icons.Default.DateRange, contentDescription = "Day",
                            )
                        })
                }
            }
        }
    }) { innerPadding ->
        dayWithExercisesAndSets.value.map {
            LazyColumn(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(16.dp),
            ) {
                items(it.exercises) { exercise ->
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp,
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                    ) {
                        ListItem(headlineContent = {
                            Text(
                                exercise.exercise.title,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        })
                        HorizontalDivider()
                        exercise.sets.map { set ->
                            SetListItem(
                                set = set, viewModel = viewModel
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SetListItem(modifier: Modifier = Modifier, set: Set, viewModel: DayViewModel) {
    var checked by remember { mutableStateOf(false) }
    var repState by remember { mutableStateOf("") }
    var weightState by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    ListItem(headlineContent = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("${set.setId}")
            Text(
                "${set.weight}kg"
            )
            Text(
                "${set.reps} reps",
            )
            Row {
                IconButton(onClick = {
                    showDialog = true
                    repState = set.reps.toString()
                    weightState = set.weight.toString()
                }) {
                    Icon(
                        Icons.Outlined.Edit,
                        contentDescription = "Edit",
                    )
                }
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    },
                )
            }

        }
        if (showDialog) {
            UpdateDialog(onDismissRequest = { showDialog = false },
                weightValue = weightState,
                onWeightValueChange = { weightState = it },
                repsValue = repState,
                onRepsValueChange = { repState = it },
                onConfirm = {
                    coroutineScope.launch {
                        viewModel.updateSet(
                            set.copy(
                                weight = weightState.toInt(),
                                reps = repState.toInt()
                            )
                        )
                    }
                }

            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    weightValue: String,
    onWeightValueChange: (String) -> Unit,
    repsValue: String,
    onRepsValueChange: (String) -> Unit,
    onConfirm: () -> Unit
) {

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5))
            .background(Color.White)
            .fillMaxWidth()
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            OutlinedTextField(
                value = weightValue,
                onValueChange = {
                    onWeightValueChange(it)
                },
                label = { Text("Weight") },
                modifier = modifier.weight(1f)
            )
            OutlinedTextField(
                value = repsValue,
                onValueChange = {
                    onRepsValueChange(it)
                },
                label = { Text("Reps") },
                modifier = modifier.weight(1f)
            )
            Button(
                onClick = {
                    onConfirm()
                    onDismissRequest()
                }, modifier = modifier
                    .weight(1f)
                    .height(54.dp)
            ) {
                Text("Update", textAlign = TextAlign.Center)
            }
        }
    }
}





