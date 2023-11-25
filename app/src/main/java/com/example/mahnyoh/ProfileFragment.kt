package com.example.mahnyoh

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.mahnyoh.databinding.FragmentHomeBinding
import com.example.mahnyoh.databinding.FragmentProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase



class ProfileFragment : Fragment(), ProfileUpdateListener {
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