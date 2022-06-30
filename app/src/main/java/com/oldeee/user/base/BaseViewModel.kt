package com.oldeee.user.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading

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
}