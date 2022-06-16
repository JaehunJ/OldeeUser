package com.oldeee.user.repository

import android.content.SharedPreferences
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import javax.inject.Inject

class CommonRepository @Inject constructor(api: OldeeService, prefs: SharedPreferences):BaseRepository(api, prefs) {
    suspend fun requestVersionInfo() = call{
        api.requestVersionInfo()
    }
}