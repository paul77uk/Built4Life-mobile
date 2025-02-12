package com.example.built4life.data.repos

import com.example.built4life.data.daos.DayDao
import com.example.built4life.data.entities.Day
import com.example.built4life.data.entities.DayExerciseCrossRef
import com.example.built4life.data.relations.DayWithExercises
import com.example.built4life.data.relations.DayWithExercisesAndSets
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DayRepository {

    fun getAllDays(): Flow<List<Day>>

    suspend fun insertDay(day: Day)

    fun getDaysWithExercises(dayId: Int): Flow<List<DayWithExercises>>

    suspend fun insertDayExerciseCrossRef(dayExerciseCrossRef: DayExerciseCrossRef)

    fun getDaysWithExercisesAndSets(programId: Int): Flow<List<DayWithExercisesAndSets>>

    fun getDayWithExercisesAndSets(dayId: Int): Flow<List<DayWithExercisesAndSets>>
}

class DayRepositoryImpl @Inject constructor(private val dayDao: DayDao) :
    DayRepository {

    override fun getAllDays(): Flow<List<Day>> = dayDao.getAllDays()

    override suspend fun insertDay(day: Day) = dayDao.insertDay(day)

    override fun getDaysWithExercises(dayId: Int): Flow<List<DayWithExercises>> =
        dayDao.getDaysWithExercises(dayId)

    override suspend fun insertDayExerciseCrossRef(dayExerciseCrossRef: DayExerciseCrossRef) =
        dayDao.insertDayExerciseCrossRef(dayExerciseCrossRef)

    override fun getDaysWithExercisesAndSets(programId: Int): Flow<List<DayWithExercisesAndSets>> =
        dayDao.getDaysWithExercisesAndSets(programId)

    override fun getDayWithExercisesAndSets(dayId: Int): Flow<List<DayWithExercisesAndSets>> =
        dayDao.getDayWithExercisesAndSets(dayId)


}