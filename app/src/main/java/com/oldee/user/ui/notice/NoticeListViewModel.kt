package com.oldee.user.ui.notice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.response.NoticeResponseData
import com.oldee.user.usercase.GetNoticeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoticeListViewModel @Inject constructor(private val getNoticeListUseCase: GetNoticeListUseCase):BaseViewModel(){
    val noticeList = MutableLiveData<List<NoticeResponseData>>()

    fun requestNoticeList(){
        remote {
            val result = getNoticeListUseCase.invoke()

            result?.let{
                noticeList.postValue(it.data)
            }
        }
    }
}