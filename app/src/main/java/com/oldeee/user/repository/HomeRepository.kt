package com.oldeee.user.repository

import android.content.SharedPreferences
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(api:OldeeService, preferences: SharedPreferences):BaseRepository(api, preferences) {
    suspend fun requestDesignList() = call { api.requestDesignList(getAccessToken(),10, 0) }
    suspend fun requestExpertList() = call { api.requestExpertList(getAccessToken())}
}