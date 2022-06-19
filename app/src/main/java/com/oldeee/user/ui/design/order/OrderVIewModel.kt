package com.oldeee.user.ui.design.order

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.oldeee.user.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderVIewModel @Inject constructor() : BaseViewModel() {
    var detailInfo:String = ""

    val imageData = MutableLiveData<MutableList<Uri>>()

    fun addPhoto(uri:Uri){
        val newList =imageData.value?: mutableListOf()
        newList.add(uri)

        imageData.postValue(newList.toMutableList())
    }

    fun addPhoto(newData:List<Uri>){
        val oldList = imageData.value?: mutableListOf()
        newData.forEach {
            oldList.add(it)
        }

        imageData.postValue(oldList.toMutableList())
    }
}