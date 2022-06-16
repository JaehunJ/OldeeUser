package com.oldeee.user.ui.splash

import androidx.lifecycle.viewModelScope
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(repository: CommonRepository):BaseViewModel(repository) {

    fun requestVersionInfo(){
        viewModelScope.launch {
            val result = getRepository<CommonRepository>().requestVersionInfo()

            result?.let{

            }
        }
    }
}