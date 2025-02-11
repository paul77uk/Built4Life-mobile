package com.example.built4life.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.built4life.data.entities.Set
import com.example.built4life.data.relations.ExerciseWithSets
import kotlinx.coroutines.flow.Flow

@Dao
interface SetDao {

    @Query("SELECT * FROM `set`")
    fun getAllSets(): Flow<List<Set>>

    @Insert
    suspend fun insertSet(set: Set)

    @Query("SELECT * FROM exercise WHERE exercise_id = :exerciseId")
    fun getExercisesWithSets(exerciseId: Int): Flow<List<ExerciseWithSets>>

    @Update
    suspend fun updateSet(set: Set)

}