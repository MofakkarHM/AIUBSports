package com.example.aiubsports

data class Tournament(
    val tournamentId: String? = null,
    val name: String? = null,
    val sportType: String? = null,
    val lastDate: String? = null,
    val status: String? = "Open"
)