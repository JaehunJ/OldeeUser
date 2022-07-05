package com.oldee.user

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OldeeUserApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.e("#debug", "asd")

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}