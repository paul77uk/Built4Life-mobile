package com.example.built4life.hiltmodules

import com.example.built4life.data.repos.ProgramRepository
import com.example.built4life.data.repos.ProgramRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindProgramRepository(programRepositoryImpl: ProgramRepositoryImpl): ProgramRepository
}