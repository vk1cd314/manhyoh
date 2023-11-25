package com.example.mahnyoh

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import de.hdodenhof.circleimageview.CircleImageView

class EditProfileDialogFragment : DialogFragment() {
    var profileUpdateListener: ProfileUpdateListener? = null
    private lateinit var profileImageView: CircleImageView
    private lateinit var nameEditText: EditText
    private var selectedImageUri: Uri? = null
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            profileImageView.setImageURI(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_edit_profile, container, false)

        profileImageView = view.findViewById(R.id.profileImageView)
        nameEditText = view.findViewById(R.id.nameEditText)
        val saveButton = view.findViewById<Button>(R.id.saveButton)

        profileImageView.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            Log.i("HELLO", name)
            profileUpdateListener?.onProfileUpdated(name, selectedImageUri)
            dismiss() // Close the dialog
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setStyle(STYLE_NORMAL, R.style.FullScreenMaterialDialog)
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.dialog_edit_profile, container, false)
//        profileImageView = view.findViewById(R.id.profileImageView)
//
//        profileImageView.setOnClickListener {
//            pickImageFromGallery()
//        }
//
//
//        return view
//    }

}
