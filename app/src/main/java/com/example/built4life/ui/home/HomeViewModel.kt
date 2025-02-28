package com.example.built4life.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.built4life.data.entities.Program
import com.example.built4life.data.repos.DayRepository
import com.example.built4life.data.repos.ProgramRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val programRepository: ProgramRepository,
    private val dayRepository: DayRepository,
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        viewModelScope.launch {
            programRepository.getAllPrograms().collect { programs ->
                _homeUiState.update {
                    it.copy(programs = programs)
                }
            }
        }
    }

    suspend fun insertProgram(program: Program) {
        programRepository.insertProgram(program)
    }

    suspend fun updateProgram(program: Program) {
        programRepository.updateProgram(program)
    }

    suspend fun deleteProgram(program: Program) {
        programRepository.deleteProgram(program)
    }
}