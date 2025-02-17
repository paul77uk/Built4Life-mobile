package com.example.built4life.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.built4life.data.entities.Program
import com.example.built4life.data.relations.ProgramWithDays
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDao {
    @Query("SELECT * FROM program")
    fun getAllPrograms(): Flow<List<Program>>

    @Insert
    suspend fun insertProgram(program: Program)

    @Update
    suspend fun updateProgram(program: Program)

    @Delete
    suspend fun deleteProgram(program: Program)

    @Transaction
    @Query("SELECT * FROM program WHERE program_id = :programId")
    fun getProgramsWithDays(programId: Int): Flow<List<ProgramWithDays>>

}