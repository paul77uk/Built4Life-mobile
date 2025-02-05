package com.example.built4life.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.built4life.data.entities.Exercise
import com.example.built4life.data.relations.ExerciseWithDays
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<Exercise>>

    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Transaction
    @Query("SELECT * FROM exercise WHERE exercise_id = :exerciseId")
    fun getExercisesWithDays(exerciseId: Int): Flow<List<ExerciseWithDays>>

}