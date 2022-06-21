package com.oldeee.user.ui.design.add

import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.DesignDetailData
import com.oldeee.user.usercase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCartViewModel @Inject constructor(val getImageUseCase: GetImageUseCase) : BaseViewModel() {
    var detailInfo:String = ""

    val imageData = MutableLiveData<MutableList<Uri>>()

    var reformData:DesignDetailData? = null

//    var prepareItemList


    fun setImage(imageView: ImageView, uri:Uri){
        Glide.with(imageView.context).load(uri).into(imageView)
    }

    fun addPhoto(newData:List<Uri>){
        val oldList = imageData.value?: mutableListOf()
        newData.forEach {
            oldList.add(it)
        }

        imageData.postValue(oldList.toMutableList())
    }

    fun setPrepareItem(code:String){

    }
}