package com.oldeee.user.ui.home

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.DesignListItem
import com.oldeee.user.network.response.ExpertListItem
import com.oldeee.user.usercase.GetDesignListUseCase
import com.oldeee.user.usercase.GetExpertListUseCase
import com.oldeee.user.usercase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDesignListUseCase: GetDesignListUseCase,
    private val expertListUseCase: GetExpertListUseCase,
    private val getImageUseCase: GetImageUseCase
) : BaseViewModel() {

    val designList = MutableLiveData<List<DesignListItem>>()
    val expertList = MutableLiveData<List<ExpertListItem>>()

    fun requestDesignList() {
        remote {
            val result = getDesignListUseCase.invoke(10, 0)
            result?.let {
                designList.postValue(it.data)
            }
        }
    }

    fun requestExpertList() {
        remote {
            val result = expertListUseCase.invoke()

            result?.let {
                expertList.postValue(it.data)
            }
        }
    }

    fun setImage(imageView: ImageView, path:String){
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            imageView.clipToOutline = true
            Glide.with(imageView).load(bitmap).centerCrop().into(imageView)
        }
    }

    fun setImageCircle(imageView: ImageView, path: String) {
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(imageView).load(bitmap).apply(RequestOptions().circleCrop()).into(imageView)
        }
    }
}