package com.example.built4life.ui.day

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.built4life.customcomposables.AppBar

@Composable
fun DayPage(
    modifier: Modifier = Modifier, navigateUp: () -> Unit, viewModel: DayViewModel = hiltViewModel()
) {
    val title = viewModel.dayRoute.title
    val programWithDays = viewModel.programWithDays.collectAsState(initial = emptyList())
    val dayWithExercises = viewModel.dayWithExercises.collectAsState(initial = emptyList())
    var exercisesWithSets = viewModel.exercisesWithSets.collectAsState(initial = emptyList())
    var dayWithExercisesAndSets =
        viewModel.dayWithExercisesAndSets.collectAsState(initial = emptyList())
    var chosenDay by remember { mutableIntStateOf(0) }
//    val coroutineScope = rememberCoroutineScope()
    Scaffold(topBar = {
        AppBar(
            modifier = modifier, title = title, canNavigateBack = true, navigateUp = navigateUp
        )
    }, bottomBar = {
        NavigationBar {
            programWithDays.value.map {
                it.days.forEachIndexed { index, day ->
                    NavigationBarItem(
                        selected = index == chosenDay,
//                        enabled = index != chosenDay,
                        onClick = {
                            chosenDay = index
                            viewModel.getExercises(day.dayId)
                        },
                        label = {
                            Text(text = day.title)
                        },
                        alwaysShowLabel = true,
                        icon = {
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
                            defaultElevation = 6.dp
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
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    fontWeight = FontWeight.SemiBold,
                                )
                            },
                            supportingContent = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                ) {
                                    Column {
                                        Text("Sets")
                                        exercise.sets.forEachIndexed { index, _ ->
                                            Text("${index + 1}")
                                        }
                                    }
                                    Column {
                                        Text("Reps")
                                        exercise.sets.map {
                                            Text(it.reps.toString())
                                        }
                                    }
                                    Column {
                                        Text("Weight")
                                        exercise.sets.map {
                                            Text(it.weight.toString())
                                        }
                                    }
                                }

                            },
                        )
                    }
                }
            }
        }
    }
}

