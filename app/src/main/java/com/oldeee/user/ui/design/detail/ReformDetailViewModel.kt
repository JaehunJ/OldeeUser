package com.oldeee.user.ui.design.detail

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.DesignDetailData
import com.oldeee.user.usercase.GetDesignDetailUseCase
import com.oldeee.user.usercase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReformDetailViewModel @Inject constructor(
    private val getDesignDetailUseCase: GetDesignDetailUseCase,
    private val getImageUseCase: GetImageUseCase
) : BaseViewModel() {

    val res = MutableLiveData<DesignDetailData>()
    val totalImageCnt = MutableLiveData<Int>()
    val currentImageIdx = MutableLiveData<Int>()

    fun requestDesignDetail(id:Int){
        remote {
            val result = getDesignDetailUseCase.invoke(id)

            result?.let{
                res.postValue(it.data)
            }
        }
    }

    fun setImage(imageView:ImageView, path:String){
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(imageView.context).load(bitmap).into(imageView)
        }
    }

    fun setImageCircle(imageView: ImageView, path: String){
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(imageView.context).load(bitmap).apply(RequestOptions().circleCrop()).into(imageView)
        }
    }
}