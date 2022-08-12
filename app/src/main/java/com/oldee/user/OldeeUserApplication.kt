package com.oldee.user

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OldeeUserApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        FirebaseCrashlytics.getInstance().apply {
            setCrashlyticsCollectionEnabled(!com.google.firebase.crashlytics.BuildConfig.DEBUG)
        }

        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .methodCount(0)
            .showThreadInfo(false).build()
        Logger.addLogAdapter(object :AndroidLogAdapter(formatStrategy){
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}