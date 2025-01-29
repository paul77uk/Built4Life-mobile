package com.example.built4life.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.built4life.data.entities.Program
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDao {
    @Query("SELECT * FROM program")
    fun getAllPrograms(): Flow<List<Program>>

    @Insert
    suspend fun insertProgram(program: Program)
}