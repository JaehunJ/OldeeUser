package com.oldee.user.ui.splash

import androidx.lifecycle.MutableLiveData
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.response.VersionInfoData
import com.oldee.user.usercase.GetVersionInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getVersionInfoUseCase: GetVersionInfoUseCase) :
    BaseViewModel() {

    val data = MutableLiveData<VersionInfoData?>()

    fun requestVersionInfo() {
        remote {
            val result = getVersionInfoUseCase.invoke()

            result?.let {

                if (it.errorMessage.isNullOrEmpty()) {
                    data.postValue(it.data[0])
                }
            }
        }
    }
}