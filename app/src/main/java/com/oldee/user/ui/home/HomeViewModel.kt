package com.oldee.user.ui.home

import android.content.res.Resources
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.oldee.user.R
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.response.DesignListItem
import com.oldee.user.network.response.ExpertListItem
import com.oldee.user.usercase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDesignListUseCase: GetDesignListUseCase,
    private val expertListUseCase: GetExpertListUseCase,
    private val getImageUseCase: GetImageUseCase,
    private val getUserData:GetUserData,
    private val setImageUseCase: SetImageUseCase,
    private val setImageCircleUseCase: SetImageCircleUseCase
) : BaseViewModel() {

    val designList = MutableLiveData<List<DesignListItem>>()
    val expertList = MutableLiveData<List<ExpertListItem>>()

    fun call(onEnd: () -> Unit){
        remote(false) {
            requestExpertListSuspend()
            requestDesignListSuspend()
            onEnd()
        }
    }

    fun getUserName():String{
        val data = getUserData.invoke()?.userName

        return data?:""
    }

    suspend fun requestExpertListSuspend(){
        val result = expertListUseCase.invoke()

        result?.let {
            expertList.postValue(it.data)
        }
    }

    suspend fun requestDesignListSuspend(){
        val result = getDesignListUseCase.invoke(6, 0)
        result?.let {
            designList.postValue(it.data)
        }
    }

    fun requestExpertList() {
        remote(false) {
            val result = expertListUseCase.invoke()

            result?.let {
                expertList.postValue(it.data)
            }
        }
    }

    fun setImage(imageView: ImageView, path:String){
        remote(false) {
//            val bitmap = getImageUseCase.invoke(path)
            imageView.clipToOutline = true
            setImageUseCase.invoke(imageView.context, imageView, path)

//            Glide.with(imageView).load(bitmap).centerCrop().into(imageView)
        }
    }

    fun setImage(imageView: ImageView, path:String, roundInt: Int){
        remote(false) {
            setImageUseCase.invoke(imageView.context, imageView, path, roundInt)
        }
    }

    fun setImageCircle(imageView: ImageView, path: String) {
        remote(false) {
            setImageCircleUseCase.invoke(imageView.context, imageView, path)
//            val bitmap = getImageUseCase.invoke(path)
//            Glide.with(imageView).load(bitmap).apply(RequestOptions().circleCrop()).into(imageView)
        }
    }
}