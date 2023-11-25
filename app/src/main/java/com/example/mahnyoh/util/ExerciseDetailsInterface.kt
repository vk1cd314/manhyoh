package com.example.mahnyoh.util

import com.example.mahnyoh.ExDetails
import java.io.Serializable

interface ExerciseDetailsFactory  {
    fun createExerciseDetails(): ExDetails
}
