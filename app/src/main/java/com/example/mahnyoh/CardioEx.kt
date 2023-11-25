package com.example.mahnyoh

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mahnyoh.ui.theme.MahnYohTheme
import com.example.mahnyoh.util.CardioExerciseDetailsFactory
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CardioEx : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cardioex)

        val start=findViewById<ExtendedFloatingActionButton>(R.id.cardiostart)

        start.setOnClickListener{

            val cardioFactory = CardioExerciseDetailsFactory()
            val cardioDetails = cardioFactory.createExerciseDetails()
            val intent = Intent(this,ExDetails::class.java).apply {
                putExtra("exerciseName", "Cardio")


            }


            startActivity(intent)
        }
    }
}