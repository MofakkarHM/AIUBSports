package com.example.aiubsports

data class Slot(
    val slotId: String? = null,
    val type: String? = null,
    val date: String? = null,
    val time: String? = null,
    val status: String? = "Available",
    val bookedBy: String? = null
)