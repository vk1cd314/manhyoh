package com.example.mahnyoh

import android.content.Intent
import android.os.Bundle
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
import com.example.mahnyoh.util.StrengthExerciseDetailsFactory
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class StrengthEx : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.strengthex)

        val start=findViewById<ExtendedFloatingActionButton>(R.id.strengthStart)

        start.setOnClickListener{

            val strengthFactory = StrengthExerciseDetailsFactory()
            val cardioDetails = strengthFactory.createExerciseDetails()
            val intent = Intent(this,ExDetails::class.java).apply {
                putExtra("exerciseName", "Strength")


            }


            startActivity(intent)
        }
    }
}