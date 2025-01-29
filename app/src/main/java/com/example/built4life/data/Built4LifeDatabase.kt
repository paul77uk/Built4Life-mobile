package com.example.built4life.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.built4life.data.daos.ProgramDao
import com.example.built4life.data.entities.Program

@Database(
    entities = [Program::class], version = 2,
    autoMigrations = [AutoMigration(
        from = 1, to = 2,
    )]
)
abstract class Built4LifeDatabase : RoomDatabase() {
    abstract fun programDao(): ProgramDao

    companion object {
        private const val DATABASE_NAME = "built4life_database"

        @Volatile
        private var Instance: Built4LifeDatabase? = null

        fun getDatabase(context: Context): Built4LifeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, Built4LifeDatabase::class.java, DATABASE_NAME
                ).addCallback(PrepopulateRoomCallback(context))
                    .addMigrations(MIGRATION_1_2)
                    .build().also { Instance = it }
            }
        }
    }

}