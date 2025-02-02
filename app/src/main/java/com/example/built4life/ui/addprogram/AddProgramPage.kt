package com.example.built4life.ui.addprogram

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.built4life.R
import com.example.built4life.customcomposables.AppBar

@Composable
fun AddProgramPage(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            AppBar(
                modifier = modifier,
                canNavigateBack = true,
                navigateUp = navigateUp,
                title = stringResource(R.string.create_program)
            )
        },
    ) {
        Text("Add Program Page", modifier = modifier.padding(it))
    }
}