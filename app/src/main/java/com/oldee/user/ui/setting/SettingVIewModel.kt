package com.oldee.user.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.request.WithdrawRequest
import com.oldee.user.network.response.FooterResponse
import com.oldee.user.usercase.GetFooterUseCase
import com.oldee.user.usercase.GetUserData
import com.oldee.user.usercase.PostWithdraw
import com.oldee.user.usercase.StartLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingVIewModel @Inject constructor(
    private val getUserData: GetUserData,
    private val postWithdraw: PostWithdraw,
    private val startLogoutUseCase: StartLogoutUseCase,
    private val getFooterUseCase: GetFooterUseCase
) : BaseViewModel() {

    val success = MutableLiveData<Boolean>()

    val footer = MutableLiveData<FooterResponse.FooterData>()

    fun requestWithdraw() {
        remote {
            val data = getUserData.invoke()

            data?.let {
                val request = WithdrawRequest(
                    userEmail = it.userEmail,
                    userSnsId = it.snsId
                )
                val result = postWithdraw.invoke(request)

                result?.let {
//                    startLogoutUseCase.invoke()
                    success.postValue(true)
                }
//                success.postValue(true)

            }
        }
    }

    fun logout(onNext:()->Unit){
        remote {
            startLogoutUseCase.invoke()
//            success.postValue(true)
            onNext()
        }
    }

    fun requestFooter(){
        remote{
            val result = getFooterUseCase()
            result?.let{
                if(it.errorMessage.isNullOrEmpty()){
                    footer.postValue(result.data)
                }
            }
        }
    }
}