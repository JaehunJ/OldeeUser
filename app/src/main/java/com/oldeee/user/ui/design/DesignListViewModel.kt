package com.oldeee.user.ui.design

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.DesignListItem
import com.oldeee.user.repository.DesignRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignListViewModel @Inject constructor(repository: DesignRepository) :
    BaseViewModel(repository) {

    val listResponse = MutableLiveData<MutableList<DesignListItem>>()

    init {
        listResponse.value = mutableListOf()
    }

    fun requestDesignList(limit: Int, page: Int) {
        viewModelScope.launch {
            val result = getRepository<DesignRepository>().requestDesignList(limit, page)

            result?.let{d->
                listResponse.postValue(d.data.toMutableList())
            }
        }
    }
}