package com.oldee.user.ui.setting.withdraw

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.user.base.BaseViewModel
import com.oldee.user.usercase.StartLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawDoneViewModel @Inject constructor(private val startLogoutUseCase: StartLogoutUseCase):BaseViewModel() {

    val success = MutableLiveData<Boolean>()

    fun logout(){
        remote {
            startLogoutUseCase.invoke()
            success.postValue(true)
        }
    }
}