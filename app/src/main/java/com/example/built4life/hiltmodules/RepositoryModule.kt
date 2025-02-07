package com.example.built4life.hiltmodules

import com.example.built4life.data.repos.DayRepository
import com.example.built4life.data.repos.DayRepositoryImpl
import com.example.built4life.data.repos.ExerciseRepository
import com.example.built4life.data.repos.ExerciseRepositoryImpl
import com.example.built4life.data.repos.ProgramRepository
import com.example.built4life.data.repos.ProgramRepositoryImpl
import com.example.built4life.data.repos.SetRepository
import com.example.built4life.data.repos.SetRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindProgramRepository(programRepositoryImpl: ProgramRepositoryImpl): ProgramRepository

    @Binds
    abstract fun bindDayRepository(dayRepositoryImpl: DayRepositoryImpl): DayRepository

    @Binds
    abstract fun bindExerciseRepository(exerciseRepositoryImpl: ExerciseRepositoryImpl): ExerciseRepository

    @Binds
    abstract fun bindSetRepository(setRepositoryImpl: SetRepositoryImpl): SetRepository
}