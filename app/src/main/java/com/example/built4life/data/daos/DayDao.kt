package com.example.built4life.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.built4life.data.entities.Day
import com.example.built4life.data.entities.DayExerciseCrossRef
import com.example.built4life.data.relations.DayWithExercises
import com.example.built4life.data.relations.DayWithExercisesAndSets
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Query("SELECT * FROM day")
    fun getAllDays(): Flow<List<Day>>

    @Insert
    suspend fun insertDay(day: Day)

    @Transaction
    @Query("SELECT * FROM day WHERE day_id = :dayId")
    fun getDaysWithExercises(dayId: Int): Flow<List<DayWithExercises>>

    @Insert
    suspend fun insertDayExerciseCrossRef(dayExerciseCrossRef: DayExerciseCrossRef)

    @Transaction
    @Query("SELECT * FROM day WHERE program_id = :programId")
    fun getDaysWithExercisesAndSets(programId: Int): Flow<List<DayWithExercisesAndSets>>

    @Transaction
    @Query("SELECT * FROM day WHERE day_id = :dayId")
    fun getDayWithExercisesAndSets(dayId: Int): Flow<List<DayWithExercisesAndSets>>

}