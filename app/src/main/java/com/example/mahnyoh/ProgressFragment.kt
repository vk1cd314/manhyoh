package com.example.mahnyoh

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp


class ProgressFragment : Fragment() {

    private val exercises = listOf(
        Exercise(
            id = 1,
            name = "Morning Run",
            description = "A brisk run around the neighborhood.",
            steps = 4000,
            heartRate = 130,
            speed = 8.0f,
            note = "Felt energetic and refreshed.",
            type = "Running"
        ),
        Exercise(
            id = 2,
            name = "Evening Walk",
            description = "A calm and peaceful walk in the park.",
            steps = 2000,
            heartRate = 80,
            speed = 3.0f,
            note = "Very relaxing, great weather.",
            type = "Walking"
        ),
        Exercise(
            id = 3,
            name = "Cycling Adventure",
            description = "Cycling through the countryside.",
            steps = 0,   // Steps may not be relevant for cycling
            heartRate = 120,
            speed = 15.0f,
            note = "Challenging but exhilarating.",
            type = "Cycling"
        ),
        // Add more exercises as needed
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                ExerciseList(exercises)
            }
        }
    }

    @Composable
    fun ExerciseList(exercises: List<Exercise>) {
        LazyColumn {
            items(exercises) { exercise ->
                ExerciseCard(exercise)
            }
        }
    }

    @Composable
    fun ExerciseCard(exercise: Exercise) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = exercise.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = exercise.description, style = MaterialTheme.typography.bodyMedium)
                Divider(Modifier.padding(vertical = 8.dp))
                Text(text = "Type: ${exercise.type}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Steps: ${exercise.steps}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Heart Rate: ${exercise.heartRate} bpm", style = MaterialTheme.typography.bodySmall)
                Text(text = "Speed: ${exercise.speed} km/h", style = MaterialTheme.typography.bodySmall)
                Text(text = "Note: ${exercise.note}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}