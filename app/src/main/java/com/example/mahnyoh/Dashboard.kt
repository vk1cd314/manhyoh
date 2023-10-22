package com.example.mahnyoh

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mahnyoh.databinding.DashboardBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataDeleteRequest
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.DataUpdateRequest
import com.google.android.gms.fitness.result.DataReadResponse
import com.google.android.gms.tasks.Task
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit

enum class FitActionRequestCode {
    INSERT_AND_READ_DATA,
    UPDATE_AND_READ_DATA,
    DELETE_DATA
}
const val TAG = "APIIIIIIIII"
class Dashboard : AppCompatActivity() {
    private lateinit var binding : DashboardBinding
    private val dateFormat = DateFormat.getDateInstance()
    private val fitnessOptions = FitnessOptions.builder()
        .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
        .build()
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       
        setContentView(R.layout.dashboard)

        binding = DashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.profile -> replaceFragment(ProfileFragment())
                R.id.progress -> replaceFragment(ProgressFragment())
                else -> {

                }
            }

            true
        }

        fitSignIn(FitActionRequestCode.INSERT_AND_READ_DATA)
    }

    override fun onResume() {
        super.onResume()

    }

    fun DataPoint.getStartTimeString(): String = DateFormat.getTimeInstance()
        .format(this.getStartTime(TimeUnit.MILLISECONDS))

    fun DataPoint.getEndTimeString(): String = DateFormat.getTimeInstance()
        .format(this.getEndTime(TimeUnit.MILLISECONDS))

    private fun fitSignIn(requestCode: FitActionRequestCode) {
        if (oAuthPermissionsApproved()) {
            performActionForRequestCode(requestCode)
        } else {
            requestCode.let {
                GoogleSignIn.requestPermissions(
                    this,
                    requestCode.ordinal,
                    getGoogleAccount(), fitnessOptions)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.i(TAG, resultCode.toString())
        Log.i(TAG, RESULT_OK.toString())
        when (resultCode) {
            RESULT_OK -> {
                val postSignInAction = FitActionRequestCode.values()[requestCode]
                performActionForRequestCode(postSignInAction)
            }
            else -> oAuthErrorMsg(requestCode, resultCode)
        }
    }

    private fun performActionForRequestCode(requestCode: FitActionRequestCode) = when (requestCode) {
        FitActionRequestCode.INSERT_AND_READ_DATA -> insertAndReadData()
        FitActionRequestCode.UPDATE_AND_READ_DATA -> updateAndReadData()
        FitActionRequestCode.DELETE_DATA -> deleteData()
    }

    private fun oAuthErrorMsg(requestCode: Int, resultCode: Int) {
        val message = """
            There was an error signing into Fit. Check the troubleshooting section of the README
            for potential issues.
            Request code was: $requestCode
            Result code was: $resultCode
        """.trimIndent()
        Log.e(TAG, message)
    }

    private fun oAuthPermissionsApproved() = GoogleSignIn.hasPermissions(getGoogleAccount(), fitnessOptions)

    private fun getGoogleAccount() = GoogleSignIn.getAccountForExtension(this, fitnessOptions)

    private fun insertAndReadData() = readHistoryData()

    private fun insertData(): Task<Void> {
        val dataSet = insertFitnessData()

        Log.i(TAG, "Inserting the dataset in the History API.")
        return Fitness.getHistoryClient(this, getGoogleAccount())
            .insertData(dataSet)
            .addOnSuccessListener { Log.i(TAG, "Data insert was successful!") }
            .addOnFailureListener { exception ->
                Log.e(TAG, "There was a problem inserting the dataset.", exception)
            }
    }

    private fun readHistoryData(): Task<DataReadResponse> {
        val readRequest = queryFitnessData()

        return Fitness.getHistoryClient(this, getGoogleAccount())
            .readData(readRequest)
            .addOnSuccessListener { dataReadResponse ->
                printData(dataReadResponse)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem reading the data.", e)
            }
    }

    private fun insertFitnessData(): DataSet {
        Log.i(TAG, "Creating a new data insert request.")

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val now = Date()
        calendar.time = now
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.HOUR_OF_DAY, -1)
        val startTime = calendar.timeInMillis

        val dataSource = DataSource.Builder()
            .setAppPackageName(this)
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setStreamName("$TAG - step count")
            .setType(DataSource.TYPE_RAW)
            .build()

        val stepCountDelta = 950
        return DataSet.builder(dataSource)
            .add(
                DataPoint.builder(dataSource)
                .setField(Field.FIELD_STEPS, stepCountDelta)
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build()
            ).build()
    }

    private fun queryFitnessData(): DataReadRequest {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val now = Date()
        calendar.time = now
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.WEEK_OF_YEAR, -1)
        val startTime = calendar.timeInMillis

        Log.i(TAG, "Range Start: ${dateFormat.format(startTime)}")
        Log.i(TAG, "Range End: ${dateFormat.format(endTime)}")

        return DataReadRequest.Builder()
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA, DataType.TYPE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()
    }

    private fun printData(dataReadResult: DataReadResponse) {
        if (dataReadResult.buckets.isNotEmpty()) {
            Log.i(TAG, "Number of returned buckets of DataSets is: " + dataReadResult.buckets.size)
            for (bucket in dataReadResult.buckets) {
                bucket.dataSets.forEach { dumpDataSet(it) }
            }
        } else if (dataReadResult.dataSets.isNotEmpty()) {
            Log.i(TAG, "Number of returned DataSets is: " + dataReadResult.dataSets.size)
            dataReadResult.dataSets.forEach { dumpDataSet(it) }
        }
    }

    private fun dumpDataSet(dataSet: DataSet) {
        Log.i(TAG, "Data returned for Data type: ${dataSet.dataType.name}")

        Log.i(TAG, dataSet.dataPoints.size.toString())
        val what = findViewById<TextView>(R.id.stepTracker)
        var wot: String = ""
        for (dp in dataSet.dataPoints) {
            Log.i(TAG, "Data point:")
            Log.i(TAG, "\tType: ${dp.dataType.name}")
            Log.i(TAG, "\tStart: ${dp.getStartTimeString()}")
            Log.i(TAG, "\tEnd: ${dp.getEndTimeString()}")
            dp.dataType.fields.forEach {
                Log.i(TAG, "\tField: ${it.name} Value: ${dp.getValue(it)}")
                wot += it.name + " " + dp.getValue(it).toString() + " "
            }
            wot += " "
        }
        what.text = wot
    }

    private fun deleteData() {
        Log.i(TAG, "Deleting today's step count data.")

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val now = Date()
        calendar.time = now
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val startTime = calendar.timeInMillis

        val request = DataDeleteRequest.Builder()
            .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .build()

        Fitness.getHistoryClient(this, getGoogleAccount())
            .deleteData(request)
            .addOnSuccessListener {
                Log.i(TAG, "Successfully deleted today's step count data.")
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to delete today's step count data.", e)
            }
    }

    private fun updateAndReadData() = updateData().continueWithTask { readHistoryData() }

    private fun updateData(): Task<Void> {
        val dataSet = updateFitnessData()
        val startTime = dataSet.dataPoints[0].getStartTime(TimeUnit.MILLISECONDS)
        val endTime = dataSet.dataPoints[0].getEndTime(TimeUnit.MILLISECONDS)
        Log.i(TAG, "Updating the dataset in the History API.")

        val request = DataUpdateRequest.Builder()
            .setDataSet(dataSet)
            .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()

        return Fitness.getHistoryClient(this, getGoogleAccount())
            .updateData(request)
            .addOnSuccessListener { Log.i(TAG, "Data update was successful.") }
            .addOnFailureListener { e ->
                Log.e(TAG, "There was a problem updating the dataset.", e)
            }
    }

    private fun updateFitnessData(): DataSet {
        Log.i(TAG, "Creating a new data update request.")

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val now = Date()
        calendar.time = now
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.MINUTE, -50)
        val startTime = calendar.timeInMillis

        val dataSource = DataSource.Builder()
            .setAppPackageName(this)
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setStreamName("$TAG - step count")
            .setType(DataSource.TYPE_RAW)
            .build()

        val stepCountDelta = 1000
        return DataSet.builder(dataSource)
            .add(DataPoint.builder(dataSource)
                .setField(Field.FIELD_STEPS, stepCountDelta)
                .setTimeInterval(startTime, endTime, TimeUnit.MILLISECONDS)
                .build()
            ).build()
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}

