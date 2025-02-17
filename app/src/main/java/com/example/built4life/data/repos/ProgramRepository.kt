package com.example.built4life.data.repos

import com.example.built4life.data.daos.ProgramDao
import com.example.built4life.data.entities.Program
import com.example.built4life.data.relations.ProgramWithDays
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProgramRepository {
    fun getAllPrograms(): Flow<List<Program>>
    suspend fun insertProgram(program: Program)
    fun getProgramsWithDays(programId: Int): Flow<List<ProgramWithDays>>
    suspend fun updateProgram(program: Program)
    suspend fun deleteProgram(program: Program)
}

class ProgramRepositoryImpl @Inject constructor(private val programDao: ProgramDao) :
    ProgramRepository {
    override fun getAllPrograms(): Flow<List<Program>> = programDao.getAllPrograms()

    override suspend fun insertProgram(program: Program) = programDao.insertProgram(program)

    override fun getProgramsWithDays(programId: Int): Flow<List<ProgramWithDays>> =
        programDao.getProgramsWithDays(programId)

    override suspend fun updateProgram(program: Program) = programDao.updateProgram(program)

    override suspend fun deleteProgram(program: Program) = programDao.deleteProgram(program)


}