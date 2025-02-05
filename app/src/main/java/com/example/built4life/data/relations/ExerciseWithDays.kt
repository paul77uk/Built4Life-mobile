package com.example.built4life.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.built4life.data.entities.Day
import com.example.built4life.data.entities.DayExerciseCrossRef
import com.example.built4life.data.entities.Exercise

data class ExerciseWithDays(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "day_id",
        associateBy = Junction(DayExerciseCrossRef::class)
    )
    val days: List<Day>
)
