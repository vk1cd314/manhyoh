package com.example.mahnyoh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

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

@Composable
fun NutritionScreen() {
    val context = LocalContext.current

//    val protein by viewModel.protein.collectAsState()
//    val carbs by viewModel.carbs.collectAsState()
//    val fats by viewModel.fats.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    var protein by remember { mutableStateOf(0f) }
    var carbs by remember { mutableStateOf(0f) }
    var fats by remember { mutableStateOf(0f) }

    if (showDialog) {
        NutritionEditDialog(
            onDismissRequest = { showDialog = false },
            onSave = { newProtein, newCarbs, newFats ->
                protein = newProtein
                carbs = newCarbs
                fats = newFats
                showDialog = false
            },
            initialProtein = protein,
            initialCarbs = carbs,
            initialFats = fats
        )
    }

    var onClick = {->
        showDialog = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(5f)
            ) {
                NutritionCard(title = "Weight", value = "-- kg", onClick = onClick)
            }
            Column(
                modifier = Modifier.weight(5f)
            ) {
                NutritionCard(title = "Age", value = "-- years", onClick = onClick)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        NutritionCard(title = "Calorie Expenditure", value = "-- kcal", onClick = onClick)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(3f)
            ) {
                NutritionCard(title = "Protein", value = if (protein > 0) protein.toString()+"g" else "-- g", editable = true, onClick)
            }
            Column(
                modifier = Modifier.weight(3f)
            ) {
                NutritionCard(title = "Carbs", value = if (carbs > 0) carbs.toString()+"g" else "-- g", editable = true, onClick)
            }
            Column(
                modifier = Modifier.weight(3f)
            ) {
                NutritionCard(title = "Fats", value = if (fats > 0) fats.toString()+"g" else "-- g", editable = true, onClick)
            }
        }
    }
}

@Composable
fun NutritionCard(title: String, value: String, editable: Boolean = false, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
            Text(text = value, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
            if (editable) {
                IconButton(
                    onClick = {
                        onClick()
                    },
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

@Composable
fun NutritionEditDialog(
    onDismissRequest: () -> Unit,
    onSave: (protein: Float, carbs: Float, fats: Float) -> Unit,
    initialProtein: Float = 0f,
    initialCarbs: Float = 0f,
    initialFats: Float = 0f
) {
    var protein by remember { mutableStateOf(initialProtein) }
    var carbs by remember { mutableStateOf(initialCarbs) }
    var fats by remember { mutableStateOf(initialFats) }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Adjust Nutrition Values", style = MaterialTheme.typography.bodyLarge)

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        Text("Protein")
                    }
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        Text(protein.toString())
                    }
                }

                Slider(value = protein, onValueChange = { protein = it }, valueRange = 0f..200f)

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        Text("Carbs")
                    }
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        Text(carbs.toString())
                    }
                }
                Slider(value = carbs, onValueChange = { carbs = it }, valueRange = 0f..200f)

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        Text("Fats")
                    }
                    Column(
                        modifier = Modifier.weight(5f)
                    ) {
                        Text(fats.toString())
                    }
                }
                Slider(value = fats, onValueChange = { fats = it }, valueRange = 0f..200f)

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    TextButton(onClick = { onSave(protein, carbs, fats) }) {
                        Text("Save")
                    }
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