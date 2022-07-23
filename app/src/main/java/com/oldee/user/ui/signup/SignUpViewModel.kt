package com.oldee.user.ui.signup

import androidx.lifecycle.MutableLiveData
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.request.SignUpRequest
import com.oldee.user.usercase.SetSignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val setSignUpUseCase: SetSignUpUseCase) :
    BaseViewModel() {
    var nickName = ""
    var email = ""
    var phone = ""

    //service policy
    val checkService = MutableLiveData<Boolean>()

    //개인정보 alert
    val checkPrivacy = MutableLiveData<Boolean>()

    val success = MutableLiveData<Boolean>()

    //disable
    val emailCode = MutableLiveData<String>()

    val isValidateCheck:Boolean
    get() = checkPrivacy.value ?: false && checkPrivacy.value ?: false

    val isValidate: Boolean
        get() {
            return nickName.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && checkPrivacy.value ?: false && checkPrivacy.value ?: false
        }


    fun requestSignUp(phone: String, snsId: String) {
        remote {
            val data = SignUpRequest(
                nickName,
                email,
                if (checkPrivacy.value != true) 0 else 1,
                phone,
                0,
                if (checkService.value != true) 0 else 1,
                "aos",
                "naver",
                snsId
            )
            val result = setSignUpUseCase.invoke(data)

            result?.let {
                it.data?.let { str ->
                    success.postValue(str.contains("Success"))
                }
            }
        }
    }
}