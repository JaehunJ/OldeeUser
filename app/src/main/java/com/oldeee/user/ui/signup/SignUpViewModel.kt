package com.oldeee.user.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.request.SignUpRequest
import com.oldeee.user.repository.SignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(repository: SignRepository) : BaseViewModel(repository) {
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


    fun requestSignUp(phone: String, snsId: String, ) {
        viewModelScope.launch {
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
            val result = getRepository<SignRepository>().requestSignUp(data)

            result?.let{
                it.data?.let{str->
                    if(str.contains("Success")){
                        success.postValue(true)
                    }
                }
            }
        }
    }
}