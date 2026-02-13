package com.arlabs.raksha

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RakshaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        try {
            val appInfo = packageManager.getApplicationInfo(packageName, android.content.pm.PackageManager.GET_META_DATA)
            val apiKey = appInfo.metaData.getString("com.google.android.geo.API_KEY")
            
            if (!com.google.android.libraries.places.api.Places.isInitialized() && !apiKey.isNullOrEmpty()) {
                com.google.android.libraries.places.api.Places.initialize(applicationContext, apiKey)
            }
        } catch (e: Exception) {
            // Handle exception (e.g., NameNotFoundException)
             e.printStackTrace()
        }
    }
}