package com.example.built4life.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise")
data class Exercise(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "exercise_id") val exerciseId: Int = 0,
    val title: String,
    @ColumnInfo(name = "one_rep_max") val oneRepMax: Int?,
)
