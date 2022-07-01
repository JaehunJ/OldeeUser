package com.oldeee.user.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.request.WithdrawRequest
import com.oldeee.user.usercase.GetUserData
import com.oldeee.user.usercase.PostWithdraw
import com.oldeee.user.usercase.StartLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingVIewModel @Inject constructor(
    private val getUserData: GetUserData,
    private val postWithdraw: PostWithdraw,
    private val startLogoutUseCase: StartLogoutUseCase
) : BaseViewModel() {

    val success = MutableLiveData<Boolean>()

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
                    startLogoutUseCase.invoke()
                }
                success.postValue(true)

            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            startLogoutUseCase.invoke()
            success.postValue(true)
        }
    }
}