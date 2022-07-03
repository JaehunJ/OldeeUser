package com.oldeee.user.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.response.BaseResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    private val _pushingToast = MutableLiveData<String>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val pushingToast : LiveData<String>
        get() = _pushingToast

    var baseOnError: ((String) -> Unit)? = null

    fun remote(useProgressBar: Boolean = true, action: suspend () -> Unit) {
        viewModelScope.launch {
            if (useProgressBar) {
                _isLoading.postValue(true)
            }

            action()

            if (useProgressBar) {
                _isLoading.postValue(false)
            }
        }
    }

    suspend fun remoteSuspend(useProgressBar: Boolean = true, action: suspend () -> Unit) {
        if (useProgressBar) {
            _isLoading.postValue(true)
        }

        action()

        if (useProgressBar) {
            _isLoading.postValue(false)
        }
    }

    fun postDelay(action: () -> Unit, milisec: Long) {
        viewModelScope.launch {
            delay(milisec)
            action()
        }
    }

    fun isValidResponse(res:BaseResponse) = res.errorMessage.isNullOrEmpty()
}