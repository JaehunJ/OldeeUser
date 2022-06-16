package com.oldeee.user.ui.signup

import androidx.lifecycle.viewModelScope
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.repository.SignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(repository: SignRepository):BaseViewModel(repository) {
    fun requestSignUp(){
        viewModelScope.launch {

        }
    }
}