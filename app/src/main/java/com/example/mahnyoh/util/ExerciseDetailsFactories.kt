package com.example.mahnyoh.util

import com.example.mahnyoh.ExDetails
import java.io.Serializable

class CardioExerciseDetailsFactory : ExerciseDetailsFactory {
    override fun createExerciseDetails(): ExDetails {
        return ExDetails("Cardio")
    }
}

class SquatExerciseDetailsFactory : ExerciseDetailsFactory {
    override fun createExerciseDetails(): ExDetails {
        return ExDetails("Squats")
    }
}

// Add more factories for other exercise types
