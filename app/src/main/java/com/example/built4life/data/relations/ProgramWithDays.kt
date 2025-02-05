package com.example.built4life.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.built4life.data.entities.Day
import com.example.built4life.data.entities.Program

data class ProgramWithDays(
    @Embedded val program: Program,
    @Relation(
        parentColumn = "program_id",
        entityColumn = "program_id"
    )
    val days: List<Day>
)
