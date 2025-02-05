package com.example.built4life.destinations

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object AddProgramRoute

@Serializable
data class DayRoute(val programId: Int, val title: String)