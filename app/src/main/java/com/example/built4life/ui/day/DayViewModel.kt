package com.example.built4life.ui.day

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.built4life.data.relations.DayWithExercises
import com.example.built4life.data.repos.DayRepository
import com.example.built4life.data.repos.ProgramRepository
import com.example.built4life.destinations.DayRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val programRepository: ProgramRepository,
    private val dayRepository: DayRepository
) : ViewModel() {
    val dayRoute: DayRoute = savedStateHandle.toRoute<DayRoute>()
    val programWithDays = programRepository.getProgramsWithDays(dayRoute.programId)
    var dayWithExercises = MutableStateFlow<List<DayWithExercises>>(emptyList())
//    var chosenDay = MutableLiveData(0)

    init {
        viewModelScope.launch {
            programRepository.getProgramsWithDays(dayRoute.programId).collect {
                it.map { programWithDays ->
                    if (programWithDays.days.isNotEmpty())
                        dayRepository.getDaysWithExercises(programWithDays.days[0].dayId)
                            .collect { dayWithExercisesItem ->
                                dayWithExercises.value = dayWithExercisesItem
                            }
                }

            }
        }
    }

    suspend fun getExercises(dayId: Int) {
        dayRepository.getDaysWithExercises(dayId).collect {
            dayWithExercises.value = it
        }
    }

//    private val _dayUiState = MutableStateFlow(DayUiState())
//    val dayUiState = _dayUiState.asStateFlow()
}