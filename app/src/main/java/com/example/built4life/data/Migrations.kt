package com.example.built4life.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("INSERT OR REPLACE INTO program (title) VALUES ('Program 5')")
    }
}