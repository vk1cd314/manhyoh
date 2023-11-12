package com.example.mahnyoh

import android.app.Application
import com.example.mahnyoh.data.HealthConnectManager

class BaseApplication : Application() {
    val healthConnectManager by lazy {
        HealthConnectManager(this)
    }
}