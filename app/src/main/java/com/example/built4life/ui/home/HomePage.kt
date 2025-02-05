package com.example.built4life.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.built4life.R
import com.example.built4life.customcomposables.AppBar

@Composable
fun HomePage(
    navigateToAddProgram: () -> Unit,
    navigateToDay: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.homeUiState.collectAsState()
    Scaffold(topBar = {
        AppBar(
            modifier = modifier, stringResource(R.string.app_name)
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = navigateToAddProgram,
            containerColor = Color(0xFFFE9900),
        ) {
            Icon(Icons.Filled.Add, "Add Program", tint = Color.White)

        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.programs) { program ->
                ListItem(
                    modifier = Modifier.border(0.5.dp, Color.LightGray), headlineContent = {
                        Text(program.title,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigateToDay(
                                        program.programId,
                                        program.title
                                    )
                                })
                    }, shadowElevation = 3.dp
                )
            }
        }
    }
}