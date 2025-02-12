package com.example.built4life.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.built4life.data.entities.Exercise
import com.example.built4life.data.entities.Set

data class ExerciseWithSets(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "exercise_id"
    )
    val sets: List<Set>,
)