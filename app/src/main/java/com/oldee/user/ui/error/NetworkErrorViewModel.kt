package com.oldee.user.ui.error

import androidx.lifecycle.viewModelScope
import com.oldee.user.base.BaseViewModel
import com.oldee.user.usercase.SetAutoLoginValue
import com.oldee.user.usercase.StartLogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkErrorViewModel @Inject constructor(
    private val setAutoLoginValue: SetAutoLoginValue,
    private val startLogoutUseCase: StartLogoutUseCase
):BaseViewModel() {
//    fun setAutoLogin(boolean: Boolean) = setAutoLoginValue.invoke(boolean)

    fun logout(){
        viewModelScope.launch {
            startLogoutUseCase.invoke()
        }
    }

}