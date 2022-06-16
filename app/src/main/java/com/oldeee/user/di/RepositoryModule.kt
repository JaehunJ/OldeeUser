package com.oldeee.user.di

import android.content.Context
import android.content.SharedPreferences
import com.oldeee.user.BuildConfig
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import com.oldeee.user.repository.CommonRepository
import com.oldeee.user.repository.SignInRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCommonRepository(api: OldeeService, preferences: SharedPreferences) =
        CommonRepository(api, preferences)

    @Singleton
    @Provides
    fun provideSingInRepository(api: OldeeService, preferences: SharedPreferences) =
        SignInRepository(api, preferences)

    @Singleton
    @Provides
    fun provideBaseRepository(api: OldeeService, preferences: SharedPreferences) =
        BaseRepository(api, preferences)

    @Singleton
    @Provides
    fun providePreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
}