package com.example.built4life.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day")
data class Day(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "day_id") val dayId: Int = 0,
    val title: String,
    @ColumnInfo(name = "program_id") val programId: Int,
)