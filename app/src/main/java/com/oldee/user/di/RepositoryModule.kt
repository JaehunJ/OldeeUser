package com.oldee.user.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.oldee.user.base.BaseRepository
import com.oldee.user.network.OldeeService
import com.oldee.user.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.lang.RuntimeException
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
    fun provideUserRepository(api: OldeeService, preferences: SharedPreferences) = UserRepository(api, preferences)

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
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context, masterKey: MasterKey) : SharedPreferences{
        try{
            return EncryptedSharedPreferences.create(context,
                "oldee_secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }catch (e:Exception){
            e.printStackTrace()
//            Log.e("#debug", e.)
            throw RuntimeException("Failed to create encrypted shared preferences")
        }
    }

    @Singleton
    @Provides
    fun provideMasterKey(@ApplicationContext context: Context) = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
}