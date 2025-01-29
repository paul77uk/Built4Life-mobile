package com.example.built4life.ui.home

import com.example.built4life.data.entities.Program

data class HomeUiState(
    val programs: List<Program> = emptyList(),
)