package com.example.mahnyoh

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.lifecycleScope
import com.example.mahnyoh.data.HealthConnectManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.request.DataReadRequest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Objects
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var healthConnectManager: HealthConnectManager
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Set<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        healthConnectManager = HealthConnectManager(requireContext())

        requestPermissionLauncher = registerForActivityResult(healthConnectManager.requestPermissionsActivityContract()) { grantedPermissions ->
            if (grantedPermissions.containsAll(permissions)) {
                // Permissions were granted, proceed with accessing Health Connect data
                Log.i("WORK", "works")
            } else {
                Log.i("WORK", "Doesn't work")
                // Handle the case where some or all permissions are not granted
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = 69
        val cardView = view.findViewById<CardView>(R.id.ViewSteps)
        cardView.setOnClickListener {
            val intent = Intent(activity, StepsActivity::class.java)
            startActivity(intent)
        }
        healthConnectManager = HealthConnectManager(view.context)
        val perms = healthConnectManager.requestPermissionsActivityContract()
        perms.
//        lifecycleScope.launch {
//            val what = healthConnectManager.readTodayStepCount()
//            Log.i("STEPZ", what.toString())
//        }
    }

    companion object {
        private val permissions = setOf(
            HealthPermission.getReadPermission(StepsRecord::class)
        )
    }

}