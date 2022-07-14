package com.oldee.user.ui.design

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.oldee.user.R
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.response.DesignListItem
import com.oldee.user.usercase.GetDesignListUseCase
import com.oldee.user.usercase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DesignListViewModel @Inject constructor(private val getDesignListUseCase: GetDesignListUseCase, private val getImageUseCase: GetImageUseCase):
    BaseViewModel() {

    var limit = 10
    var page = 0

    val listResponse = MutableLiveData<MutableList<DesignListItem>>()

    val resSize:Int
    get() {
        return listResponse.value?.size ?: 0
    }

    init {
        listResponse.value = mutableListOf()
    }

    fun requestDesignList(limit: Int, page: Int, isAdded:Boolean) {
        remote{
            this.page = page
            val result = getDesignListUseCase.invoke(limit, page)

            result?.let{d->
//                if(isAdded){
//                    val newList = mutableListOf<DesignListItem>()
//                    val oldList = listResponse.value
//
//                    oldList?.let{l->
//                        newList.addAll(l)
//                    }
//
//                    newList.addAll(d.data)
//                    listResponse.postValue(newList.toMutableList())
//                }else{
                    listResponse.postValue(d.data.toMutableList())
//                }
            }
        }
    }

    fun setImage(imageView: ImageView, path:String){
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)

            val requestOptions = RequestOptions()

            Glide.with(imageView.context).load(bitmap).placeholder(R.drawable.icon_empty_image).error(R.drawable.icon_empty_image).into(imageView)
        }
    }
}