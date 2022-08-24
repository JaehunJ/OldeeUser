package com.oldee.user.ui.design.detail

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oldee.user.R
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.response.DesignDetailData
import com.oldee.user.usercase.GetDesignDetailUseCase
import com.oldee.user.usercase.GetImageUseCase
import com.oldee.user.usercase.SetImageCircleUseCase
import com.oldee.user.usercase.SetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReformDetailViewModel @Inject constructor(
    private val getDesignDetailUseCase: GetDesignDetailUseCase,
    private val getImageUseCase: GetImageUseCase,
    private val setImageUseCase: SetImageUseCase,
    private val setImageCircleUseCase: SetImageCircleUseCase
) : BaseViewModel() {

    val res = MutableLiveData<DesignDetailData>()
    val totalImageCnt = MutableLiveData<Int>()
    val currentImageIdx = MutableLiveData<Int>()

    fun requestDesignDetail(id:Int){
        remote(false) {
            val result = getDesignDetailUseCase.invoke(id)

            result?.let{
                res.postValue(it.data)
            }
        }
    }

    fun setImage(imageView:ImageView, path:String){
        remote(false) {
            setImageUseCase(imageView.context, imageView, path)
        }
    }

    fun setImage(imageView:ImageView, path:String, roundInt:Int){
        remote(false) {
            setImageUseCase(imageView.context, imageView, path, roundInt)
        }
    }

    fun setImageCircle(imageView: ImageView, path: String){
        remote(false) {
            setImageCircleUseCase(imageView.context, imageView, path)
        }
    }
}