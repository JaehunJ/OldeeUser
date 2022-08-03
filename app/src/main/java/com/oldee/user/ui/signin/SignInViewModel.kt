package com.oldee.user.ui.signin

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
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.RemoteData
import com.oldee.user.network.request.NaverSignInRequest
import com.oldee.user.network.response.SignInResponseData
import com.oldee.user.usercase.*
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
    private val setAutoLoginValue: SetAutoLoginValue,
    private val setUserData: SetUserData
) : BaseViewModel() {

    val nProfile = MutableLiveData<NidProfile?>()
    val needSignIn = MutableLiveData<Boolean>()
    val res = MutableLiveData<SignInResponseData>()

    init {
        needSignIn.value = false
        nProfile.value = null
    }

    fun getAutoLogin() = getAutoLoginValue()
    fun setAutoLogin(boolean: Boolean) = setAutoLoginValue(boolean)

    fun startNaverLogin(context: Context) {
        remote {
            NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorMsg = NaverIdLoginSDK.getLastErrorDescription()

                }

                override fun onSuccess() {

                    getProfileData()
                }
            })
        }

    }

    fun getProfileData() {
        remote {
            NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorMsg = NaverIdLoginSDK.getLastErrorDescription()

                }

                override fun onSuccess(result: NidProfileResponse) {
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


                var errorData:RemoteData.ApiError? = null
                val result = setNaverSignInUseCase(data) {
                    errorData = it
                    return@setNaverSignInUseCase
                }



                if (result == null) {
                    if(errorData?.errorMessage?.contains("Match User") == true){
                        onError()
                    }else{
                        baseOnError?.invoke(errorData?.errorMessage?:"알수없는 에러")
//                        onError()
                    }
                } else {
                    if(!isValidResponse(result)){
                        baseOnError?.invoke(result.errorMessage?:"알수없는 에러")
                    }else{
                        val data = result.data
                        setAutoLogin(true)
                        setTokenUseCase(accessToken, refreshToken)
                        setUserData(
                            data.userName,
                            data.userEmail,
                            data.userPhone,
                            nProfile.value?.id ?: ""
                        )
                        res.postValue(data)
                    }
                }

                nProfile.value = null
                needSignIn.value = false
            }
        }
    }

//    fun setUserData(name: String, email: String, phone: String, snsId: String) {
//        setUserData.invoke(name, email, phone, snsId)
//    }
}