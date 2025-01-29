package com.example.built4life.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "program")
data class Program(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)
