package com.example.built4life.data.repos

import com.example.built4life.data.daos.ExerciseDao
import com.example.built4life.data.entities.Exercise
import com.example.built4life.data.relations.ExerciseWithDays
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ExerciseRepository {

    fun getAllExercises(): Flow<List<Exercise>>

    suspend fun insertExercise(exercise: Exercise)

    fun getExercisesWithDays(exerciseId: Int): Flow<List<ExerciseWithDays>>
}

class ExerciseRepositoryImpl @Inject constructor(private val exerciseDao: ExerciseDao) :
    ExerciseRepository {
    override fun getAllExercises(): Flow<List<Exercise>> = exerciseDao.getAllExercises()

    override suspend fun insertExercise(exercise: Exercise) = exerciseDao.insertExercise(exercise)

    override fun getExercisesWithDays(exerciseId: Int): Flow<List<ExerciseWithDays>> =
        exerciseDao.getExercisesWithDays(exerciseId)

}