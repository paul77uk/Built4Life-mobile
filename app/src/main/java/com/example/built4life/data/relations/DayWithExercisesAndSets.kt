package com.example.built4life.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.built4life.data.entities.Day
import com.example.built4life.data.entities.DayExerciseCrossRef
import com.example.built4life.data.entities.Exercise

data class DayWithExercisesAndSets(
    @Embedded val day: Day,
    @Relation(
        entity = Exercise::class,
        parentColumn = "day_id",
        entityColumn = "exercise_id",
        associateBy = Junction(
            DayExerciseCrossRef::class,
            parentColumn = "day_id",
            entityColumn = "exercise_id"
        )
    )
    val exercises: List<ExerciseWithSets>
)