package com.example.mahnyoh.data

import androidx.health.connect.client.records.SleepSessionRecord
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlin.random.Random

fun dateTimeWithOffsetOrDefault(time: Instant, offset: ZoneOffset?): ZonedDateTime =
    if (offset != null) {
        ZonedDateTime.ofInstant(time, offset)
    } else {
        ZonedDateTime.ofInstant(time, ZoneId.systemDefault())
    }

fun Duration.formatTime() = String.format(
    "%02d:%02d:%02d",
    this.toHours() % 24,
    this.toMinutes() % 60,
    this.seconds % 60
)

fun Duration.formatHoursMinutes() = String.format(
    "%01dh%02dm",
    this.toHours() % 24,
    this.toMinutes() % 60
)

fun randomSleepStage() = listOf(
    SleepSessionRecord.STAGE_TYPE_AWAKE,
    SleepSessionRecord.STAGE_TYPE_DEEP,
    SleepSessionRecord.STAGE_TYPE_LIGHT,
    SleepSessionRecord.STAGE_TYPE_OUT_OF_BED,
    SleepSessionRecord.STAGE_TYPE_REM,
    SleepSessionRecord.STAGE_TYPE_SLEEPING
).let { stages ->
    stages[Random.nextInt(stages.size)]
}