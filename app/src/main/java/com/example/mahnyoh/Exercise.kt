package com.example.mahnyoh

data class Exercise(
    val id: Int,
    val name: String,
    val description: String,
    val steps: Int,
    val heartRate: Int,    // BPM (Beats Per Minute)
    val speed: Float,      // Speed (e.g., in km/h or mph)
    val note: String,
    val type: String       // Type of exercise (e.g., Running, Walking)
)
