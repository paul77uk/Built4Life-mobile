package com.example.built4life.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.built4life.data.daos.DayDao
import com.example.built4life.data.daos.ExerciseDao
import com.example.built4life.data.daos.ProgramDao
import com.example.built4life.data.daos.SetDao
import com.example.built4life.data.entities.Day
import com.example.built4life.data.entities.DayExerciseCrossRef
import com.example.built4life.data.entities.Exercise
import com.example.built4life.data.entities.Program
import com.example.built4life.data.entities.Set

@Database(
    entities = [Program::class, Day::class, Exercise::class, DayExerciseCrossRef::class, Set::class],
    version = 1,
//    autoMigrations = [AutoMigration(
//        from = 3, to = 4,
//    )]
)
abstract class Built4LifeDatabase : RoomDatabase() {
    abstract fun programDao(): ProgramDao
    abstract fun dayDao(): DayDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun setDao(): SetDao

    companion object {
        private const val DATABASE_NAME = "built4life_database"

        @Volatile
        private var Instance: Built4LifeDatabase? = null

        fun getDatabase(context: Context): Built4LifeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, Built4LifeDatabase::class.java, DATABASE_NAME
                ).createFromAsset("database/built4life_database.db").build().also { Instance = it }
            }
        }
    }

}