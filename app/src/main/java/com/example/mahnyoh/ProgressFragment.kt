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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp


class ProgressFragment : Fragment() {

    private val exercises = listOf(
        Exercise(id = 1, name = "Push Ups", description = "A basic push up exercise"),
        Exercise(id = 2, name = "Pull Ups", description = "A basic pull up exercise"),
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
                Text(text = exercise.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }


}