package com.example.built4life.data

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("INSERT OR REPLACE INTO program (program_id, title) VALUES (7, 'Program 7')")
        db.execSQL("INSERT OR REPLACE INTO day (title, program_id) VALUES ('Day 1', 7)")
    }
}