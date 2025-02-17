package com.example.built4life.ui.day

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.built4life.data.entities.Day
import com.example.built4life.data.entities.Set
import com.example.built4life.data.relations.DayWithExercises
import com.example.built4life.data.relations.DayWithExercisesAndSets
import com.example.built4life.data.relations.ExerciseWithSets
import com.example.built4life.data.relations.ProgramWithDays
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
    val days = MutableStateFlow<List<Day>>(emptyList())
    val dayRoute: DayRoute = savedStateHandle.toRoute<DayRoute>()
    val programWithDays = MutableStateFlow<List<ProgramWithDays>>(emptyList())
    var dayWithExercises = MutableStateFlow<List<DayWithExercises>>(emptyList())
    var exercisesWithSets = MutableStateFlow<List<ExerciseWithSets>>(emptyList())
    var dayWithExercisesAndSets = MutableStateFlow<List<DayWithExercisesAndSets>>(emptyList())
//    var chosenDay = MutableLiveData(0)

    init {
        viewModelScope.launch {
            programRepository.getProgramsWithDays(dayRoute.programId).collect {
                programWithDays.value = it
                if (programWithDays.value[0].days.isNotEmpty())
                    getDayWithExercisesAndSets(it[0].days[0].dayId)
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

    fun getExerciseWithSets(exerciseId: Int) {
        viewModelScope.launch {
            setRepository.getExercisesWithSets(exerciseId).collect {
                exercisesWithSets.value = it
            }
        }
    }

    fun getDayWithExercisesAndSets(dayId: Int) {
        viewModelScope.launch {
            dayRepository.getDayWithExercisesAndSets(dayId).collect {
                dayWithExercisesAndSets.value = it
            }
        }
    }

    fun getAllDays() {
        viewModelScope.launch {
            dayRepository.getAllDays().collect {
                days.value = it
            }
        }
    }

    suspend fun updateSet(set: Set) = setRepository.updateSet(set)


//    private val _dayUiState = MutableStateFlow(DayUiState())
//    val dayUiState = _dayUiState.asStateFlow()
}