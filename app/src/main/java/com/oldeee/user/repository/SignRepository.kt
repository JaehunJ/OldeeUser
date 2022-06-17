package com.oldeee.user.repository

import android.content.SharedPreferences
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.request.NaverSignInRequest
import com.oldeee.user.network.request.SignUpRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignRepository @Inject constructor(api:OldeeService, prefs:SharedPreferences):BaseRepository(api, prefs){
    suspend fun requestNaverSignIn(data:NaverSignInRequest, onError:(RemoteData.ApiError) -> Unit) = call(onError) {
        api.requestSignInFromNaver("clo", data)
    }

    suspend fun requestSignUp(data:SignUpRequest) = call{
        api.requestSignUp("clo", data)
    }
}