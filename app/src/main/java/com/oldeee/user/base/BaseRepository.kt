package com.oldeee.user.base

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.oldeee.user.data.ACCESS_TOKEN
import com.oldeee.user.data.REFRESH_TOKEN
import com.oldeee.user.network.OldeeService
import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.request.NewTokenRequest
import com.oldeee.user.network.response.BaseResponse
import com.oldeee.user.network.response.NewTokenData
import com.oldeee.user.network.response.NewTokenResponse
import retrofit2.Response
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    val api: OldeeService,
    val prefs: SharedPreferences
) {
    private var isLoading = MutableLiveData<Boolean>()

    suspend fun <T : BaseResponse> call(
        onError: ((RemoteData.ApiError) -> Unit)? = null,
        apiCall: suspend () -> Response<T>
    ): T? {
        isLoading.postValue(true)
        val response = apiCall.invoke()
        isLoading.postValue(false)

        val result = if (response.isSuccessful) {
            if (response.body() != null && !response.body()!!.errorMessage.isNullOrEmpty()) {
                RemoteData.ApiError(response.body()!!.errorCode, response.body()!!.errorMessage)
            } else {
                RemoteData.Success(response.body()!!)
            }
        } else {
            RemoteData.ApiError(response.code().toString(), response.message())
        }

        when (result) {
            is RemoteData.Success ->
                return result.output
            is RemoteData.ApiError -> {
//                return result.output

                if (result.errorCode == "404") {
                    val msgLower = result.errorMessage

                    if (msgLower == null) {
                        onError?.invoke(result)
                        return null
                    }

                    msgLower.let { msg ->
                        val lower = msg.lowercase()

                        if (lower.contains("discontinued")) {
                            onError?.invoke(result)
                        } else if (lower.contains("token")) {
                            val re = getNewToken()

                            if(re == null){
                                Log.e("#debug", "token refresh exception")
                                null
                            }else{
                                setNewToken(re.data)
                                return call(onError) { apiCall() }
                            }
                        }else{
                            onError?.invoke(result)
                            return null
                        }
                    }
                } else {
                    onError?.invoke(result)
                    return null
                }
                //token 에러일 경우
                /*if (result.errorCode == "404") {
                    val msgLower = result.errorMessage

                    if (msgLower == null) {
                        onError?.invoke(result)
                        return null
                    }

                    msgLower.let { msg ->
                        val lower = msg.lowercase()

                        if (lower.contains("token")) {
                            //val re = getNewToken()

                            return if (re == null) {
                                Log.e("#debug", "token refresh exception")
                                null
                            } else {
                                //토큰 다시 설정하고 다시 콜
                                //setToken(re.data)
                                return call(onError) { apiCall() }
                            }
                        } else {
                            onError?.invoke(result)
                            return null
                        }
                    }
                } else {
                    onError?.invoke(result)
                    return null
                }*/
                return null
            }
            is RemoteData.Error -> {
                Log.e("#debug", result.exception.printStackTrace().toString())
//                onError?.invoke()
                return null
            }
        }

        return null
    }


    suspend fun getNewToken(): NewTokenResponse? {
        val accessToken = getAccessTokenRaw()
        val refreshToken = getRefreshToken()
        val data = NewTokenRequest(accessToken ?: "", refreshToken ?: "")
        val result = api.requestNewToken("clo", data)

        return result.body()
    }

    fun getAccessToken(): String {
        Log.e("#debug", "Bearer ${prefs.getString(ACCESS_TOKEN, "")}")
        return "Bearer ${prefs.getString(ACCESS_TOKEN, "")}"
    }

    private fun getAccessTokenRaw() = prefs.getString(ACCESS_TOKEN, "")
    private fun getRefreshToken() = prefs.getString(REFRESH_TOKEN, "")


    fun setNewToken(data: NewTokenData) {
        prefs.edit {
            putString(ACCESS_TOKEN, data.newAccessToken)
            putString(REFRESH_TOKEN, data.newRefreshToken)
            commit()
        }
    }

    /*
//
//    fun loadPrevSignData():String?{
//        val str = prefs.getString(USER_ID, "")
//        return str
//    }

    fun saveLoginData(id: String, pw: String) {
        prefs.edit {
            putString(USER_ID, id)
            putString(USER_PW, pw)
            commit()
        }
    }

    fun loadLoginData(): List<String> {
        val list = mutableListOf<String>()
        val id = prefs.getString(USER_ID, "") ?: ""
        val pw = prefs.getString(USER_PW, "") ?: ""

        if (id.isNotEmpty())
            list.add(id)

        if (pw.isNotEmpty())
            list.add(pw)

        return list
    }

    fun removeLoginData() {
        prefs.edit {
            remove(USER_ID)
            remove(USER_PW)
            remove(ACCESS_TOKEN)
            remove(REFRESH_TOKEN)
            commit()
        }
    }
    */
}