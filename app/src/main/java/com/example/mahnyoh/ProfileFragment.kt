package com.example.mahnyoh

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.mahnyoh.databinding.FragmentHomeBinding
import com.example.mahnyoh.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class ProfileFragment : Fragment(), ProfileUpdateListener {
    private var stepCountGoal = 10000
    private var sleepGoal = 8
    private var activityGoal = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editProfileButton = view.findViewById<CardView>(R.id.btnSignIn)
        editProfileButton.setOnClickListener {
//            val dialog = EditProfileDialogFragment()
//            dialog.show(requireFragmentManager(), "EditProfileDialogFragment")
            showDialog()
        }
        val editWeightButton = view.findViewById<AppCompatImageView>(R.id.editWageIV)
        val editAgeButton = view.findViewById<AppCompatImageView>(R.id.ageWageIV)
        val editGoalsButton = view.findViewById<TextView>(R.id.updateTV)
        editWeightButton.setOnClickListener {
            showWeight()
        }
        editAgeButton.setOnClickListener {
            showAge()
        }
        editGoalsButton.setOnClickListener {
            showGoalsInputDialog()
        }
    }

    private fun showWeight() {
        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.dialog_weight_input, null)
        val weightInputEditText = dialogView.findViewById<EditText>(R.id.weightInputEditText)

        // Create and show the AlertDialog
        AlertDialog.Builder(requireContext())
            .setTitle("Enter Weight")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, which ->
                val weight = weightInputEditText.text.toString()
                binding.wageTV.text = weight.toString() + " kg"
                // Handle the weight input
                // For example, update the UI or ViewModel
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun showAge() {
        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.dialog_age_input, null)
        val weightInputEditText = dialogView.findViewById<EditText>(R.id.ageInputEditText)

        // Create and show the AlertDialog
        AlertDialog.Builder(requireContext())
            .setTitle("Enter Age")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, which ->
                val weight = weightInputEditText.text.toString()
                binding.wageTV.text = weight.toString() + " y.o"
                // Handle the weight input
                // For example, update the UI or ViewModel
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun showGoalsInputDialog() {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_goals_input, null)
        val stepCountGoalEditText = dialogView.findViewById<EditText>(R.id.stepCountGoalEditText)
        val sleepGoalEditText = dialogView.findViewById<EditText>(R.id.sleepGoalEditText)
        val activityGoalEditText = dialogView.findViewById<EditText>(R.id.activityGoalEditText)

        // Build and show the dialog
        AlertDialog.Builder(requireContext())
            .setTitle("Set Your Goals")
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, which ->
                val stepCountGoal = stepCountGoalEditText.text.toString().toIntOrNull()
                val sleepGoal = sleepGoalEditText.text.toString().toFloatOrNull()
                val activityGoal = activityGoalEditText.text.toString().toFloatOrNull()
                // Handle the goals input (e.g., update ViewModel, UI or database)
                binding.stepsTV.text = stepCountGoal.toString() + " Steps"
                binding.sleepTV.text = sleepGoal.toString() + " Hours"
                binding.activityTV.text = activityGoal.toString() + " Hours"
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }


    private fun showDialog() {
        val fragmentManager = parentFragmentManager
        val dialogFragment = EditProfileDialogFragment()
        dialogFragment.profileUpdateListener = this
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction
            .add(android.R.id.content, dialogFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        val logout = requireActivity().findViewById<TextView>(R.id.logout_button)
        logout.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onProfileUpdated(name: String, imageUri: Uri?) {
        Log.i("NANI", name)
        binding.tvName.text = name
        imageUri?.let {
            binding.pfp.setImageURI(it)
        }
    }
}