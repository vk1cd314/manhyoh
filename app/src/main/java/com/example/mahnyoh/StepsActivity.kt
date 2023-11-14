package com.example.mahnyoh

import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.editor.Step
import com.example.mahnyoh.util.MarkerView
import com.example.mahnyoh.data.HealthConnectManager
import com.example.mahnyoh.data.StepInfo
import com.example.mahnyoh.util.XAxisFormatter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StepsActivity : AppCompatActivity() {
    private lateinit var healthConnectManager: HealthConnectManager
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.all { it.value }) {
            Log.i("WHAAAAT", "All required permissions granted")
        } else {
            Log.w("WHAAAT", "Not all required permissions granted")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.step_activity)

        setUpHealthConnect()
        setUpBackButton()
        setUpStepChart()
        setUpRecyclerView()
    }

    private fun setUpBackButton() {
        val backFab = findViewById<FloatingActionButton>(R.id.backButton)
        backFab.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getLastSevenDays(): List<String> {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return (0..6).map { daysAgo ->
            LocalDate.now().minusDays(daysAgo.toLong()).format(dateFormatter)
        }.reversed()
    }

    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.step_count_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

//        val stepData = listOf(
//            StepInfo("2023-01-01", 1000, 0.8),
//            StepInfo("2023-01-02", 1500, 1.2),
//            StepInfo("2023-01-02", 1500, 1.2),
//            StepInfo("2023-01-02", 1500, 1.2),
//            StepInfo("2023-01-02", 1500, 1.2),
//            StepInfo("2023-01-02", 1500, 1.2),
//            StepInfo("2023-01-02", 1500, 1.2),
//            StepInfo("2023-01-02", 1500, 1.2),
//            StepInfo("2023-01-02", 1500, 1.2),
//            StepInfo("2023-01-02", 1500, 1.2)
//        )
        val stepData: ArrayList<StepInfo> = arrayListOf()
        lifecycleScope.launch {
            val steps = healthConnectManager.readLastWeekStepDataEntriesWithDistance()
            val dateList = getLastSevenDays()
            for ((id, entry) in steps.withIndex()) {
                stepData.add(StepInfo(dateList[id] + " " + entry.first, entry.second, entry.third))
            }
            stepData.reverse()
            val adapter = StepAdapter(stepData)
            recyclerView.adapter = adapter
        }
    }


    private fun setUpHealthConnect() {
        healthConnectManager = HealthConnectManager(this)
        permissionLauncher.launch(permissions)
    }

    private fun setUpStepChart() {
        val lineChart = findViewById<LineChart>(R.id.lineChart)
        val entries = ArrayList<Entry>()
        val xAxisStrings: ArrayList<String> = arrayListOf()

        lineChart.xAxis.isEnabled = true
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM

        val markerView = MarkerView(this, R.layout.marker_view)
        lineChart.marker = markerView
        lifecycleScope.launch {
            val stepData = healthConnectManager.readLastWeekStepDataEntries()
            var id = 0
            for (entry in stepData) {
                val dayOfWeek = entry.first
                val dailyTotalSteps = entry.second

                xAxisStrings.add(dayOfWeek)
                entries.add(Entry(id.toFloat(), dailyTotalSteps.toFloat()))
                id += 1
            }
            lineChart.xAxis.valueFormatter = XAxisFormatter(xAxisStrings)

            val lineDataSet = LineDataSet(entries, "Label").apply {
                axisDependency = YAxis.AxisDependency.LEFT
                color = Color.BLACK
                setDrawValues(false)

                setDrawCircles(true)
                setCircleColor(Color.RED)
                circleRadius = 5f

                val gradientColors = intArrayOf(Color.GREEN, Color.TRANSPARENT)
                val gradient = LinearGradient(
                    0f, 0f, 0f, lineChart.height.toFloat(),
                    gradientColors, null, Shader.TileMode.MIRROR
                )
                fillDrawable = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, gradientColors)
                setDrawFilled(true)

                mode = LineDataSet.Mode.CUBIC_BEZIER

                val dashLength = 20f
                val spaceLength = 10f
                val phase = 0f
                val dashPathEffect = DashPathEffect(floatArrayOf(dashLength, spaceLength), phase)
                enableDashedLine(dashLength, spaceLength, phase)

            }

            lineChart.legend.isEnabled = false
            val description = Description().apply {
                text = "Step Counts For the Last 7 days"
                textSize = 10f
                textColor = Color.GRAY
            }
            lineChart.description = description
            val lineData = LineData(lineDataSet)
            lineChart.data = lineData
            lineChart.invalidate()
        }

    }

    public class CustomDataEntry(x: String, value: Number) : ValueDataEntry(x, value)
    companion object {
        private val permissions = arrayOf(
            HealthPermission.getReadPermission(StepsRecord::class),
            HealthPermission.getReadPermission(DistanceRecord::class)
        )
    }
}