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

    var totalPrice = MutableLiveData<Int>()

    init {
        name = "정재훈"
        phone = "01088335697"
        postNum = "08763"
        address = "서울특별시 관악구 남부순환로165길 59 (신림동, 미래)"
        extendAddress = "204호"
    }

    fun setImage(imageView: ImageView, path:String){
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            imageView.clipToOutline = true
            Glide.with(imageView).load(bitmap).centerCrop().into(imageView)
        }
    }
}