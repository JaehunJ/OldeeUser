package com.oldeee.user.ui.signin

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfile
import com.navercorp.nid.profile.data.NidProfileResponse
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.request.NaverSignInRequest
import com.oldeee.user.network.response.SignInResponse
import com.oldeee.user.network.response.SignInResponseData
import com.oldeee.user.usercase.GetAutoLoginValue
import com.oldeee.user.usercase.SetAutoLoginValue
import com.oldeee.user.usercase.SetNaverSignInUseCase
import com.oldeee.user.usercase.SetTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val setNaverSignInUseCase: SetNaverSignInUseCase,
    private val setTokenUseCase: SetTokenUseCase,
    private val getAutoLoginValue: GetAutoLoginValue,
    private val setAutoLoginValue: SetAutoLoginValue
) : BaseViewModel() {

    val nProfile = MutableLiveData<NidProfile?>()
    val needSignIn = MutableLiveData<Boolean>()
    val res = MutableLiveData<SignInResponseData>()

    init {
        needSignIn.value = false
        nProfile.value = null
    }

    fun getAutoLogin() = getAutoLoginValue.invoke()
    fun setAutoLogin(boolean: Boolean) = setAutoLoginValue.invoke(boolean)

    fun startNaverLogin(context: Context) {
        viewModelScope.launch {
            NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorMsg = NaverIdLoginSDK.getLastErrorDescription()

                    Log.e("#debug", "naver error code->${errorCode} \n msg->${errorMsg}")
                }

                override fun onSuccess() {
                    Log.e("#debug", "naver login success")
                    getProfileData()
                }
            })
        }

    }

    fun getProfileData() {
        viewModelScope.launch {
            NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorMsg = NaverIdLoginSDK.getLastErrorDescription()

                    Log.e("#debug", "profile error code->${errorCode} \n msg->${errorMsg}")
                }

                override fun onSuccess(result: NidProfileResponse) {
                    Log.e("#debug", "get profile data success")
                    nProfile.postValue(result.profile)
                }
            })
        }
    }

    fun requestNaverSignIn(profile: NidProfile?, onError: () -> Unit) {
        val accessToken = NaverIdLoginSDK.getAccessToken()
        val refreshToken = NaverIdLoginSDK.getRefreshToken()
        val date = Date(NaverIdLoginSDK.getExpiresAt() * 1000L)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        simpleDateFormat.timeZone = TimeZone.getDefault()

        if (accessToken != null && refreshToken != null && profile != null) {
            remote(true) {
                val data = NaverSignInRequest(
                    accessToken,
                    refreshToken,
                    simpleDateFormat.format(date),
                    profile.email ?: "",
                    profile.id ?: ""
                )

                Log.e("#debug", "accessToken:${accessToken}")
                Log.e("#debug", "refreshToken:${refreshToken}")

                val result = setNaverSignInUseCase.invoke(data) {

                }

                nProfile.value = null
                needSignIn.value = false

                if (result == null) {
                    onError()
                } else {
                    setAutoLogin(true)
                    setTokenUseCase.invoke(result.data)
                    res.postValue(result.data)
//                    onNext(result.data.userName)
                }
            }
        }
    }
}