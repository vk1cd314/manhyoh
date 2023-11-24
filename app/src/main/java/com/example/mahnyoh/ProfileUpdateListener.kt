package com.example.mahnyoh

import android.net.Uri

interface ProfileUpdateListener {
    fun onProfileUpdated(name: String, imageUri: Uri?)
}