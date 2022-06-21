package com.oldeee.user.repository

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.edit
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.data.ACCESS_TOKEN
import com.oldeee.user.data.REFRESH_TOKEN
import com.oldeee.user.network.OldeeService
import com.oldeee.user.network.response.NoticeResponse
import com.oldeee.user.network.response.SignInResponseData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonRepository @Inject constructor(api: OldeeService, prefs: SharedPreferences) :
    BaseRepository(api, prefs) {
    suspend fun requestVersionInfo() = call {
        api.requestVersionInfo()
    }

    suspend fun getImageFromServer(url: String): Bitmap? {
        val result = api.requestImage(getAccessToken(), url).body()

        if (result != null) {
            return BitmapFactory.decodeStream(result.byteStream())
        }

        return null
    }

    fun setToken(data: SignInResponseData) {
        prefs.edit {
            putString(ACCESS_TOKEN, data.accessToken)
            putString(REFRESH_TOKEN, data.refreshToken)
            commit()
        }
    }

    suspend fun requestNoticeList() = call {
        api.requestNoticeList(getAccessToken())
    }
}