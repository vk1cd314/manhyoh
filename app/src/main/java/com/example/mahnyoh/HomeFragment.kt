package com.example.mahnyoh

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.SpeedRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.lifecycleScope
import com.example.mahnyoh.data.HealthConnectManager
import com.example.mahnyoh.databinding.FragmentHomeBinding
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
    }

    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
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
        val nutritionCardView = view.findViewById<CardView>(R.id.nutritionCardView)
        nutritionCardView.setOnClickListener {
            val intent = Intent(activity, NutritionActivity::class.java)
            startActivity(intent)
        }
        healthConnectManager = HealthConnectManager(view.context)
        Log.d("WHAAAT", "Checking permissions")
        permissionLauncher.launch(permissions)
        lifecycleScope.launch {
            val steps = healthConnectManager.readTodayStepCount()
            Log.i("WHAAAT", (steps * 2.0f).toString())
            progressBar.progress = (steps * 2.0f).toInt()
        }
    }

    companion object {
        private val permissions = arrayOf(
            HealthPermission.getReadPermission(StepsRecord::class),

            HealthPermission.getReadPermission(DistanceRecord::class),
            HealthPermission.getReadPermission(SpeedRecord::class),
            HealthPermission.getReadPermission(ExerciseSessionRecord::class)

        )
    }

    override fun onStart() {
        super.onStart()
        val cardio = requireActivity().findViewById<CardView>(R.id.cardio_card)
        val balance=requireActivity().findViewById<CardView>(R.id.balance_card)
        val strength=requireActivity().findViewById<CardView>(R.id.strength_card)
        val agility=requireActivity().findViewById<CardView>(R.id.agility_card)
        val flex=requireActivity().findViewById<CardView>(R.id.flex_card)
        cardio.setOnClickListener {
            val intent = Intent(context, CardioEx::class.java)
            startActivity(intent)
        }

        balance.setOnClickListener {
            val intent = Intent(context, BalanceEx::class.java)
            startActivity(intent)
        }

        strength.setOnClickListener {
            val intent = Intent(context, StrengthEx::class.java)
            startActivity(intent)
        }

        agility.setOnClickListener {
            val intent = Intent(context, AgilityEx::class.java)
            startActivity(intent)
        }

        flex.setOnClickListener {
            val intent = Intent(context, FlexibilityEx::class.java)
            startActivity(intent)
        }
    }

}