package com.oldee.user.ui.splash

import android.os.Build
import androidx.lifecycle.MutableLiveData
import com.oldee.user.BuildConfig
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

    fun isShowingUpdatePopup(serverVersion:String):Boolean{
        val serverSplit = serverVersion.split(".")
        val localSplit = BuildConfig.VERSION_NAME.split(".")

        if(serverSplit[0].toInt() > localSplit[0].toInt())
            return true

        if(serverSplit[1].toInt() > localSplit[1].toInt())
            return true

        if(serverSplit[2].toInt() > localSplit[2].toInt())
            return true

        return false
    }
}