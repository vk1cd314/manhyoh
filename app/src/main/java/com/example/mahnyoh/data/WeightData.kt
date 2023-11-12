package com.example.mahnyoh.data

import androidx.health.connect.client.units.Mass
import java.time.ZonedDateTime

data class WeightData(
    val weight: Mass,
    val id: String,
    val time: ZonedDateTime,
    val sourceAppInfo: HealthConnectAppInfo?
)