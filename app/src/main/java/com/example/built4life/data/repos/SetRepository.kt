package com.example.built4life.data.repos

import com.example.built4life.data.daos.SetDao
import com.example.built4life.data.entities.Set
import com.example.built4life.data.relations.ExerciseWithSets
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SetRepository {
    fun getAllSets(): Flow<List<Set>>
    suspend fun insertSet(set: Set)
    fun getExercisesWithSets(exerciseId: Int): Flow<List<ExerciseWithSets>>
}

class SetRepositoryImpl @Inject constructor(private val setDao: SetDao) : SetRepository {
    override fun getAllSets(): Flow<List<Set>> = setDao.getAllSets()
    override suspend fun insertSet(set: Set) = setDao.insertSet(set)
    override fun getExercisesWithSets(exerciseId: Int): Flow<List<ExerciseWithSets>> =
        setDao.getExercisesWithSets(exerciseId)
    
}
