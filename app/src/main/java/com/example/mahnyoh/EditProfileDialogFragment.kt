package com.example.mahnyoh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditProfileDialogFragment : DialogFragment() {

    private lateinit var profileImageView: ImageView

    // Register the activity result launcher
    private val pickImagesLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
        if (uris.isNotEmpty()) {
            // For simplicity, we're just handling the first image
            profileImageView.setImageURI(uris.first())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.FullScreenMaterialDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_edit_profile, container, false)
        profileImageView = view.findViewById(R.id.profileImageView)

        profileImageView.setOnClickListener {
            pickImageFromGallery()
        }

        return view
    }

    private fun pickImageFromGallery() {
        // Launch the photo picker
        pickImagesLauncher.launch("image/*")
    }
}
