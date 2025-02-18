package com.example.built4life.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.built4life.R
import com.example.built4life.customcomposables.AppBar
import com.example.built4life.customcomposables.DeleteDialog
import com.example.built4life.data.entities.Program
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    navigateToAddProgram: () -> Unit,
    navigateToDay: (Int, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.homeUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
//    var showDropDown by remember { mutableStateOf(false) }
    var programName by remember { mutableStateOf("") }
    var programNameValue by remember { mutableStateOf("") }
    var editMode by remember { mutableStateOf(false) }
    var programId by remember { mutableIntStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        AppBar(
            modifier = modifier, stringResource(R.string.app_name)
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                showDialog = true
                editMode = false
                programNameValue = ""
            },
            containerColor = Color(0xFFFE9900),
        ) {
            Icon(Icons.Filled.Add, "Add Program", tint = Color.White)

        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
//                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.End
        ) {
            items(uiState.programs) { program ->
//                    deleteComposable = {
//                        DeleteDialog(
//                            onDismissRequest = {
//                                showDeleteDialog = false
//                                showDialog = false
//                            },
//                            onDelete = {
//                                coroutineScope.launch {
//                                    viewModel.deleteProgram(program)
//                                }
//                            }
//                        )
//                    },
                ProgramListItem(
                    program = program,
                    navigateToDay = navigateToDay,
                    onClick = {
                        editMode = true
                        showDialog = true
                        programId = program.programId
                        programName = program.title
                    })
            }
        }

    }
    if (showDeleteDialog) {
        DeleteDialog(onDismissRequest = {
            showDeleteDialog = false
        }, onDelete = {
            coroutineScope.launch {
                viewModel.deleteProgram(
                    program = Program(
                        programId = programId,
                        title = programNameValue,
                    )
                )
            }
            showDeleteDialog = false
            showDialog = false
        })
    }
    if (showDialog) {
        CreateProgramDialog(
            editMode = editMode,
            onDismissRequest = { showDialog = false },
            onDeleteProgram = {
                showDeleteDialog = true
                coroutineScope.launch {
                    delay(1000)
                    showDialog = false
                }
            },
            onInsertProgram = {
                coroutineScope.launch {
                    if (editMode) {
                        viewModel.updateProgram(
                            program = Program(
                                programId = programId,
                                title = programName,
                            )
                        )
                    } else {
                        viewModel.insertProgram(
                            program = Program(
                                title = programNameValue,
                            )
                        )
                    }
                    showDialog = false
                    programNameValue = ""
                }

            },
            programName = if (editMode) programName else programNameValue,
            onProgramNameChange = { if (editMode) programName = it else programNameValue = it },
            title = if (editMode) "Edit Program" else "Create Program"
        )
    }
}

@Composable
private fun ProgramListItem(
    program: Program,
    navigateToDay: (Int, String) -> Unit,
    onClick: () -> Unit,
) {
    ListItem(modifier = Modifier.border(0.5.dp, Color.LightGray), headlineContent = {
        Text(program.title,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToDay(
                        program.programId, program.title
                    )
                }
                .padding(start = 60.dp))
    }, shadowElevation = 3.dp, trailingContent = {
        IconButton(onClick = onClick) {
            Column {
                Icon(Icons.Filled.MoreVert, "More options")
            }
        }
    })
}

@Composable
fun DropDown(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Box {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = modifier.background(Color.White),
        ) {
            DropdownMenuItem(text = { Text("Edit") }, onClick = {
                onEdit()
                onDismissRequest()
            })
            HorizontalDivider()
            DropdownMenuItem(text = { Text("Delete") }, onClick = {
                onDelete()
                onDismissRequest()
            })
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProgramDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onInsertProgram: () -> Unit,
    programName: String,
    onProgramNameChange: (String) -> Unit,
    title: String,
    onDeleteProgram: () -> Unit,
    editMode: Boolean = false
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5))
            .background(Color.White)
            .padding(16.dp),
        content = {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                OutlinedTextField(
                    value = programName,
                    onValueChange = { onProgramNameChange(it) },
                    label = { Text("Program Name") },
                    modifier = Modifier.fillMaxWidth()
                )
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
                    if (editMode) {
                        Button(
                            onClick = {
                                onDeleteProgram()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                            ),
                            shape = RoundedCornerShape(15),
                        ) {
                            Text("Delete")
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    Button(
                        onClick = onInsertProgram,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFE9900),
                        ),
                        shape = RoundedCornerShape(15),
                        enabled = programName.isNotBlank()
                    ) {
                        Text("Save")
                    }
                }
            }
        })
}


