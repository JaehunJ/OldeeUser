package com.oldee.user.di

import com.google.firebase.crashlytics.BuildConfig
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FireBaseModule {
    @Provides
    @Singleton
    fun provideFirebaseCrashlytics() = FirebaseCrashlytics.getInstance().apply {
        setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }
}