package com.example.mahnyoh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class NutritionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    NutritionScreen()
                }
            }
        }
    }
}

// Place the Composables (NutritionScreen and NutritionCard) here
@Composable
fun NutritionScreen() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutritionCard(title = "Weight", value = "-- kg")
            NutritionCard(title = "Age", value = "-- years")
        }
        Spacer(modifier = Modifier.height(8.dp))
        NutritionCard(title = "Calorie Expenditure", value = "-- kcal")
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NutritionCard(title = "Protein", value = "-- g", editable = true)
            NutritionCard(title = "Carbs", value = "-- g", editable = true)
            NutritionCard(title = "Fats", value = "-- g", editable = true)
        }
    }
}

@Composable
fun NutritionCard(title: String, value: String, editable: Boolean = false) {
    Card(
        modifier = Modifier.padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            Text(text = title, style = MaterialTheme.typography.h6)
//            Text(text = value, style = MaterialTheme.typography.subtitle1)
            Text(text = title)
            Text(text = value)
            if (editable) {
                IconButton(
                    onClick = { /* TODO: Handle edit */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewNutritionScreen() {
    NutritionScreen()
}