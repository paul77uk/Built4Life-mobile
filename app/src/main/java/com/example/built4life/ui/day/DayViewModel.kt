package com.example.built4life.ui.day

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.built4life.data.entities.Set
import com.example.built4life.data.relations.DayWithExercises
import com.example.built4life.data.relations.DayWithExercisesAndSets
import com.example.built4life.data.relations.ExerciseWithSets
import com.example.built4life.data.repos.DayRepository
import com.example.built4life.data.repos.ExerciseRepository
import com.example.built4life.data.repos.ProgramRepository
import com.example.built4life.data.repos.SetRepository
import com.example.built4life.destinations.DayRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val programRepository: ProgramRepository,
    private val dayRepository: DayRepository,
    private val setRepository: SetRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    val dayRoute: DayRoute = savedStateHandle.toRoute<DayRoute>()
    val programWithDays = programRepository.getProgramsWithDays(dayRoute.programId)
    var dayWithExercises = MutableStateFlow<List<DayWithExercises>>(emptyList())
    var exercisesWithSets = MutableStateFlow<List<ExerciseWithSets>>(emptyList())
    var dayWithExercisesAndSets = MutableStateFlow<List<DayWithExercisesAndSets>>(emptyList())
//    var chosenDay = MutableLiveData(0)

    init {
        viewModelScope.launch {
            programRepository.getProgramsWithDays(dayRoute.programId).collect {
                it.map { programWithDays ->
                    if (programWithDays.days.isNotEmpty())
                        dayRepository.getDaysWithExercisesAndSets(programWithDays.days[0].dayId)
                            .collect { dayWithExercisesItem ->
                                dayWithExercisesAndSets.value = dayWithExercisesItem
                            }
                }

            }
        }
    }

    fun getExercises(dayId: Int) {
        viewModelScope.launch {
            dayRepository.getDaysWithExercises(dayId).collect {
                dayWithExercises.value = it
            }
        }
    }

    fun getSets(dayId: Int) {
        viewModelScope.launch {
            dayRepository.getDaysWithExercisesAndSets(dayId).collect {
                dayWithExercisesAndSets.value = it
            }
        }
    }

    suspend fun updateSet(set: Set) = setRepository.updateSet(set)


//    private val _dayUiState = MutableStateFlow(DayUiState())
//    val dayUiState = _dayUiState.asStateFlow()
}