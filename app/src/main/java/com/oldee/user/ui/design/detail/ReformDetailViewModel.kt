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
        remote(false) {
            val result = getDesignDetailUseCase.invoke(id)

            result?.let{
                res.postValue(it.data)
            }
        }
    }

    fun setImage(imageView:ImageView, path:String){
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(imageView.context).load(bitmap).placeholder(R.drawable.icon_empty_image).error(R.drawable.icon_empty_image).into(imageView)
        }
    }

    fun setImageCircle(imageView: ImageView, path: String){
        remote(false) {
            if(path.isEmpty()){
                Glide.with(imageView.context).load(R.mipmap.ic_launcher_round).apply(RequestOptions().circleCrop()).into(imageView)
            }else{
                val bitmap = getImageUseCase.invoke(path)
                if(bitmap == null){
                    Glide.with(imageView.context).load(R.mipmap.ic_launcher_round).apply(RequestOptions().circleCrop()).into(imageView)
                }else{
                    Glide.with(imageView.context).load(bitmap).apply(RequestOptions().circleCrop()).into(imageView)
                }
            }
        }
    }
}