package com.example.built4life.data.repos

import com.example.built4life.data.daos.DayDao
import com.example.built4life.data.entities.Day
import com.example.built4life.data.entities.DayExerciseCrossRef
import com.example.built4life.data.relations.DayWithExercises
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface DayRepository {

    fun getAllDays(): Flow<List<Day>>

    suspend fun insertDay(day: Day)

    fun getDaysWithExercises(dayId: Int): Flow<List<DayWithExercises>>

    suspend fun insertDayExerciseCrossRef(dayExerciseCrossRef: DayExerciseCrossRef)
}

class DayRepositoryImpl @Inject constructor(private val dayDao: DayDao) :
    DayRepository {

    override fun getAllDays(): Flow<List<Day>> = dayDao.getAllDays()

    override suspend fun insertDay(day: Day) = dayDao.insertDay(day)

    override fun getDaysWithExercises(dayId: Int): Flow<List<DayWithExercises>> =
        dayDao.getDaysWithExercises(dayId)

    override suspend fun insertDayExerciseCrossRef(dayExerciseCrossRef: DayExerciseCrossRef) =
        dayDao.insertDayExerciseCrossRef(dayExerciseCrossRef)
}