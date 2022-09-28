package com.oldee.user.repository

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.edit
import com.oldee.user.base.BaseRepository
import com.oldee.user.custom.getTextBody
import com.oldee.user.data.ACCESS_TOKEN
import com.oldee.user.data.REFRESH_TOKEN
import com.oldee.user.network.OldeeService
import com.oldee.user.network.response.SignInResponseData
import okhttp3.MultipartBody
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

    suspend fun postImageToServer(list: List<MultipartBody.Part>) = call {
        api.requestPostImage(getAccessToken(), list, getTextBody("reform"))
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

    suspend fun requestFaqList() = call {
        api.requestFAQList(getAccessToken())
    }
}