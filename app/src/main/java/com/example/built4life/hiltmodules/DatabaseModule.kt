package com.example.built4life.hiltmodules

import android.content.Context
import com.example.built4life.data.Built4LifeDatabase
import com.example.built4life.data.daos.DayDao
import com.example.built4life.data.daos.ExerciseDao
import com.example.built4life.data.daos.ProgramDao
import com.example.built4life.data.daos.SetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideProgramDao(@ApplicationContext context: Context): ProgramDao =
        Built4LifeDatabase.getDatabase(context).programDao()

    @Provides
    fun provideDayDao(@ApplicationContext context: Context): DayDao =
        Built4LifeDatabase.getDatabase(context).dayDao()

    @Provides
    fun provideExerciseDao(@ApplicationContext context: Context): ExerciseDao =
        Built4LifeDatabase.getDatabase(context).exerciseDao()

    @Provides
    fun provideSetDao(@ApplicationContext context: Context): SetDao =
        Built4LifeDatabase.getDatabase(context).setDao()

}