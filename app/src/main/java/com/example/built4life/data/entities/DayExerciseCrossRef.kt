package com.example.built4life.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    primaryKeys = ["day_id", "exercise_id"],
    tableName = "day_exercise_cross_ref",
)
data class DayExerciseCrossRef(
    @ColumnInfo(name = "day_id") val dayId: Int,
    @ColumnInfo(name = "exercise_id") val exerciseId: Int,
)
