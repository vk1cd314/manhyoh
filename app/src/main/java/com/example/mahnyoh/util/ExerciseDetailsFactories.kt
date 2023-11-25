package com.example.mahnyoh.util

import com.example.mahnyoh.ExDetails
import java.io.Serializable

class CardioExerciseDetailsFactory : ExerciseDetailsFactory {
    override fun createExerciseDetails(): ExDetails {
        return ExDetails("Cardio")
    }
}

class AgilityExerciseDetailsFactory : ExerciseDetailsFactory {
    override fun createExerciseDetails(): ExDetails {
        return ExDetails("Agility")
    }
}

class FleixbilityExerciseDetailsFactory : ExerciseDetailsFactory {
    override fun createExerciseDetails(): ExDetails {
        return ExDetails("Flexibility")
    }
}

class BalanceExerciseDetailsFactory : ExerciseDetailsFactory {
    override fun createExerciseDetails(): ExDetails {
        return ExDetails("Balance")
    }
}

class StrengthExerciseDetailsFactory : ExerciseDetailsFactory {
    override fun createExerciseDetails(): ExDetails {
        return ExDetails("Strength")
    }
}



// Add more factories for other exercise types
