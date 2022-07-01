package com.oldeee.user.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.navercorp.nid.NaverIdLoginSDK
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.data.ACCESS_TOKEN
import com.oldeee.user.data.REFRESH_TOKEN
import com.oldeee.user.data.SNS_ID
import com.oldeee.user.network.OldeeService
import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.request.NaverSignInRequest
import com.oldeee.user.network.request.SignUpRequest
import com.oldeee.user.network.request.WithdrawRequest
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

    fun getAutoLoginValue() = prefs.getBoolean("auto", false)
    fun setAutoLoginValue(boolean: Boolean){
        prefs.edit {
            putBoolean("auto", boolean)
            commit()
        }
    }

    suspend fun requestWithdraw(data:WithdrawRequest) = call {
        api.requestWithdraw(getAccessToken(), data)
    }

    suspend fun logout(){
        NaverIdLoginSDK.logout()
        prefs.edit {
            remove("auto")
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            remove(SNS_ID)
            commit()
        }
    }
}