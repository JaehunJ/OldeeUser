package com.oldeee.user.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.DesignListItem
import com.oldeee.user.network.response.ExpertListItem
import com.oldeee.user.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: HomeRepository) : BaseViewModel(repository) {

    val designList = MutableLiveData<List<DesignListItem>>()
    val expertList = MutableLiveData<List<ExpertListItem>>()

    fun requestDesignList() {
        viewModelScope.launch {
            val result = getRepository<HomeRepository>().requestDesignList()

            result?.let {
                designList.postValue(it.data)
            }
        }
    }

    fun requestExpertList() {
        viewModelScope.launch {
            val result = getRepository<HomeRepository>().requestExpertList()

            result?.let {
                expertList.postValue(it.data)
            }
        }
    }
}