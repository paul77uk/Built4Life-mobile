package com.example.built4life.data.repos

import com.example.built4life.data.daos.ProgramDao
import com.example.built4life.data.entities.Program
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProgramRepository {
    fun getAllPrograms(): Flow<List<Program>>
}

class ProgramRepositoryImpl @Inject constructor(private val programDao: ProgramDao) :
    ProgramRepository {
    override fun getAllPrograms(): Flow<List<Program>> = programDao.getAllPrograms()

    suspend fun insertProgram(program: Program) = programDao.insertProgram(program)
}