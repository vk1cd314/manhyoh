package com.example.mahnyoh

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.lifecycleScope
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.example.mahnyoh.data.HealthConnectManager
import kotlinx.coroutines.launch

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

        healthConnectManager = HealthConnectManager(this)
        permissionLauncher.launch(permissions)

        val anyChartView = findViewById<AnyChartView>(R.id.any_chart_view)
        anyChartView.setProgressBar(findViewById(R.id.progress_bar)) // If you have a progress bar

        val cartesian: Cartesian = AnyChart.line()

        cartesian.animation(true)
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

        cartesian.title("Step Activity Over the Last Week")

        cartesian.yAxis(0).title("Steps Count")
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        lifecycleScope.launch {
            val seriesData = healthConnectManager.readLastWeekStepDataEntries()

            val set = com.anychart.data.Set.instantiate()
            set.data(seriesData)
            val seriesMapping = set.mapAs("{ x: 'x', value: 'value' }")

            val series1: Line = cartesian.line(seriesMapping)
            series1.name("Steps")
            series1.hovered().markers().enabled(true)
            series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4.0)
            series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5.0)
                .offsetY(5.0)

            cartesian.legend().enabled(true)
            cartesian.legend().fontSize(13.0)
            cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

            anyChartView.setChart(cartesian)
        }

    }

    public class CustomDataEntry(x: String, value: Number) : ValueDataEntry(x, value)
    companion object {
        private val permissions = arrayOf(
            HealthPermission.getReadPermission(StepsRecord::class)
        )
    }
}