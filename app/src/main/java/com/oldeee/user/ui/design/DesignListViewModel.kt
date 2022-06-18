package com.oldeee.user.ui.design

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.DesignListItem
import com.oldeee.user.repository.DesignRepository
import com.oldeee.user.usercase.GetDesignListUseCase
import com.oldeee.user.usercase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesignListViewModel @Inject constructor(private val getDesignListUseCase: GetDesignListUseCase, private val getImageUseCase: GetImageUseCase):
    BaseViewModel() {

    val listResponse = MutableLiveData<MutableList<DesignListItem>>()

    init {
        listResponse.value = mutableListOf()
    }

    fun requestDesignList(limit: Int, page: Int) {
        remote{
            val result = getDesignListUseCase.invoke(limit, page)

            result?.let{d->
                listResponse.postValue(d.data.toMutableList())
            }
        }
    }

    fun setImage(imageView: ImageView, path:String){
        remote {
            val bitmap = getImageUseCase.invoke(path)
            Glide.with(imageView.context).load(bitmap).into(imageView)
        }
    }
}