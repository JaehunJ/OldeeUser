package com.oldeee.user.di

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.oldeee.user.BuildConfig
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import com.oldeee.user.repository.*
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
    fun provideSignRepository(api: OldeeService, preferences: SharedPreferences) =
        SignRepository(api, preferences)

    @Singleton
    @Provides
    fun provideDesignRepository(api: OldeeService, preferences: SharedPreferences) =
        DesignRepository(api, preferences)

    @Singleton
    @Provides
    fun provideExpertRepository(api: OldeeService, preferences: SharedPreferences) =
        ExpertRepository(api, preferences)

    @Singleton
    @Provides
    fun provideBaseRepository(api: OldeeService, preferences: SharedPreferences) =
        BaseRepository(api, preferences)


    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context) : SharedPreferences{
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(context,
            "oldee_secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}