package com.example.built4life.ui.day

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.built4life.customcomposables.AppBar
import kotlinx.coroutines.launch

@Composable
fun DayPage(
    modifier: Modifier = Modifier, navigateUp: () -> Unit, viewModel: DayViewModel = hiltViewModel()
) {
    val title = viewModel.dayRoute.title
    val programWithDays = viewModel.programWithDays.collectAsState(initial = emptyList())
    val dayWithExercises = viewModel.dayWithExercises.collectAsState(initial = emptyList())
    var chosenDay by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
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
                        onClick = {
                            chosenDay = index
                            coroutineScope.launch {
                                viewModel.getExercises(day.dayId)
                            }
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
        dayWithExercises.value.map {
            LazyColumn(modifier = modifier.padding(innerPadding)) {
                items(it.exercises) {
                    Text(text = it.title)
                }
            }
        }

    }
}