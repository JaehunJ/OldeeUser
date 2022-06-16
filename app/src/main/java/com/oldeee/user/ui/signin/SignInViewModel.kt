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
import com.oldeee.user.repository.SignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(repository: SignRepository):BaseViewModel(repository){

    val isNaverOAuthSuccess = MutableLiveData<Boolean>()
    val nIdProfile = MutableLiveData<NidProfileResponse>()
    val goToSignUp = MutableLiveData<Boolean>()
    val isSignInSuccess = MutableLiveData<Boolean>()

    init {
        isNaverOAuthSuccess.value = false
        goToSignUp.value = false
    }

    fun startNaverLogin(context: Context){
        NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback{
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
//                val token = NaverIdLoginSDK.getAccessToken()

                getProfileData()
            }

        })
    }

    fun getProfileData(){
        NidOAuthLogin().callProfileApi(object :NidProfileCallback<NidProfileResponse>{
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorMsg = NaverIdLoginSDK.getLastErrorDescription()

                Log.e("#debug", "profile error code->${errorCode} \n msg->${errorMsg}")
            }

            override fun onSuccess(result: NidProfileResponse) {
                requestNaverSignIn(result.profile)
            }
        })
    }

    fun requestNaverSignIn(profile: NidProfile?){
        viewModelScope.launch {
            val accessToken = NaverIdLoginSDK.getAccessToken()
            val refreshToken = NaverIdLoginSDK.getRefreshToken()
            val date = Date(NaverIdLoginSDK.getExpiresAt()*1000L)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            simpleDateFormat.timeZone = TimeZone.getDefault()


            if(accessToken != null && refreshToken != null && profile!=null){
                val data = NaverSignInRequest(
                    accessToken, refreshToken, simpleDateFormat.format(date), profile.email?:"", profile.id?:""
                )

                val result = getRepository<SignRepository>().requestNaverSignIn(data){
                    it.errorMessage?.let{str->
                        if(str.contains("User")){
                            goToSignUp.postValue(true)
                        }
                    }
                }

                result?.let{
                    Log.e("#debug", "call next fragment")
                }
            }
        }
    }
}