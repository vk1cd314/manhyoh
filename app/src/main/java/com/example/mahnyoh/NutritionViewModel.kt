package com.example.mahnyoh

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NutritionViewModel : ViewModel() {
    private val _protein = MutableStateFlow(0f)
    val protein: StateFlow<Float> = _protein

    private val _carbs = MutableStateFlow(0f)
    val carbs: StateFlow<Float> = _carbs

    private val _fats = MutableStateFlow(0f)
    val fats: StateFlow<Float> = _fats

    // Method to update the values
    fun updateNutritionValues(newProtein: Float, newCarbs: Float, newFats: Float) {
        _protein.value = newProtein
        _carbs.value = newCarbs
        _fats.value = newFats
    }

}
