package com.oldeee.user.ui.payment

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.BasketListItem
import com.oldeee.user.usercase.GetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(val getImageUseCase: GetImageUseCase):BaseViewModel(){
    val datas = MutableLiveData<List<BasketListItem>>()

    var name = ""
    var phone = ""
    var postNum = ""
    var address = ""
    var extendAddress = ""

    var totalPrice = 0

    fun setImage(imageView: ImageView, path:String){
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            imageView.clipToOutline = true
            Glide.with(imageView).load(bitmap).centerCrop().into(imageView)
        }
    }
}