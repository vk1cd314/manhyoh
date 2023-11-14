package com.example.mahnyoh.util

import com.github.mikephil.charting.formatter.ValueFormatter

class XAxisFormatter(private val values: ArrayList<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return values.getOrNull(value.toInt()) ?: value.toString()
    }
}
