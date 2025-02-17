package com.example.built4life.ui.day

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.BasicAlertDialog
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
    var exercisesWithSets = viewModel.exercisesWithSets.collectAsState(initial = emptyList())
    var dayWithExercises = viewModel.dayWithExercises.collectAsState(initial = emptyList())

    Scaffold(topBar = {
        AppBar(
            modifier = modifier, title = title, canNavigateBack = true, navigateUp = navigateUp
        )
    }, bottomBar = {
        NavigationBar {
            programWithDays.value.map {
                if (it.days.isNotEmpty())
                    it.days.forEachIndexed { index, day ->
                        NavigationBarItem(selected = index == chosenDay,
//                        enabled = index != chosenDay,
                            onClick = {
                                chosenDay = index
                                viewModel.getDayWithExercisesAndSets(day.dayId)
                            }, label = {
                                Text(text = day.title)
                            }, alwaysShowLabel = true, icon = {
                                Icon(
                                    Icons.Default.DateRange, contentDescription = "Day",
                                )
                            })
                    } else {
                    Text(text = "No days found")
                }
            }
        }
    }) { innerPadding ->
        if (dayWithExercisesAndSets.value.isEmpty()) {
            Text(text = "No exercises found")
        } else
            dayWithExercisesAndSets.value.map { day ->
                LazyColumn(
                    modifier = modifier
                        .padding(innerPadding)
                        .padding(16.dp),
                ) {
                    items(day.exercises) { exercise ->
                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp,
                            ),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                        ) {
                            ListItem(
                                headlineContent = {
                                    Text(
                                        exercise.exercise.title,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.fillMaxWidth(),
                                    )
                                },
                            )
                            HorizontalDivider()

                            exercise.sets.forEachIndexed { index, set ->
                                SetListItem(
                                    set = set, viewModel = viewModel, index = index
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
fun SetListItem(modifier: Modifier = Modifier, set: Set, viewModel: DayViewModel, index: Int) {
    var checked by remember { mutableStateOf(false) }
    var repState by remember { mutableStateOf("") }
    var weightState by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    ListItem(headlineContent = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("${index + 1}")
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
            UpdateDialog(
                onDismissRequest = { showDialog = false },
                weightValue = weightState,
                onWeightValueChange = { weightState = it },
                repsValue = repState,
                onRepsValueChange = { repState = it },
                onConfirm = {
                    if (weightState.isNotBlank() && repState.isNotBlank()) {
                        coroutineScope.launch {
                            viewModel.updateSet(
                                set.copy(
                                    weight = weightState.toInt(), reps = repState.toInt()
                                )
                            )
                        }
                        showDialog = false
                    } else {
                        val toast =
                            Toast.makeText(context, "Please enter a value", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                },
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
    onConfirm: () -> Unit,
) {


    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5))
            .background(Color.White)
            .fillMaxWidth()
            .padding(8.dp),
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            OutlinedTextField(value = weightValue, onValueChange = {
                onWeightValueChange(it)
            }, label = { Text("Weight") }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            ), maxLines = 1, modifier = modifier.weight(1f)
            )
            OutlinedTextField(value = repsValue, onValueChange = {
                onRepsValueChange(it)
            }, label = { Text("Reps") }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
            ), keyboardActions = KeyboardActions(onDone = {
                onConfirm()
                //                    onDismissRequest()
            }), singleLine = true, maxLines = 1, modifier = modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    onDismissRequest()
                },
                modifier = modifier
            ) {
                Icon(
                    Icons.Rounded.Close,
                    contentDescription = "Exit",
                    modifier = modifier
                        .fillMaxSize(),
                    tint = Color.Red
                )
            }
            IconButton(
                onClick = {
                    onConfirm()
                    //                    onDismissRequest()
                }, modifier = modifier
            ) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = "Save",
                    modifier = modifier.fillMaxSize(),
                    tint = Color.Green
                )
            }
        }
    }
}





