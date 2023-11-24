package com.example.mahnyoh.data

import androidx.health.connect.client.units.Velocity

data class AggregatedHealthData(
    val avgSpeed: Velocity?,
    val maxSpeed: Velocity?,
    val minSpeed: Velocity?,
    val totalDistance: androidx.health.connect.client.units.Length?,
    val totalSteps: Long?
)