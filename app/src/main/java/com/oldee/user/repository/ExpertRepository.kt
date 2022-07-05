package com.oldee.user.repository

import android.content.SharedPreferences
import com.oldee.user.base.BaseRepository
import com.oldee.user.network.OldeeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpertRepository @Inject constructor(api: OldeeService, preferences: SharedPreferences):BaseRepository(api, preferences){
    suspend fun requestExpertList() = call { api.requestExpertList(getAccessToken())}
}