package com.example.built4life.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "set")
data class Set(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "set_id") val setId: Int = 0,
    val reps: Int,
    val weight: Int,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int
)