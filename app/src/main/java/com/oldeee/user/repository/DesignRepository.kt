package com.oldeee.user.repository

import android.content.SharedPreferences
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DesignRepository @Inject constructor(api: OldeeService, preferences: SharedPreferences) :
    BaseRepository(api, preferences) {

    suspend fun requestDesignList(limit: Int, page: Int) =
        call { api.requestDesignList(getAccessToken(), limit, page) }
}