package com.example.built4life.ui.day

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.built4life.customcomposables.AppBar

@Composable
fun DayPage(
    modifier: Modifier = Modifier, navigateUp: () -> Unit, viewModel: DayViewModel = hiltViewModel()
) {
    val title = viewModel.dayRoute.title
    val programWithDays = viewModel.programWithDays.collectAsState(initial = emptyList())
    val dayWithExercises = viewModel.dayWithExercises.collectAsState(initial = emptyList())
    Scaffold(topBar = {
        AppBar(
            modifier = modifier, title = title, canNavigateBack = true, navigateUp = navigateUp
        )
    }) { innerPadding ->
//        programWithDays.value.map {
//            LazyColumn(modifier = modifier.padding(innerPadding)) {
//                items(it.days) {
//                    Text(text = it.title)
//                }
//            }
//        }
        dayWithExercises.value.map {
            LazyColumn(modifier = modifier.padding(innerPadding)) {
                items(it.exercises) {
                    Text(text = it.title)
                }
            }
        }
    }
}