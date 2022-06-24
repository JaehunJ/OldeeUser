package com.oldeee.user.ui.payment

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.BasketListItem
import com.oldeee.user.usercase.GetImageUseCase
import com.oldeee.user.usercase.PostPaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(val getImageUseCase: GetImageUseCase, val postPaymentUseCase: PostPaymentUseCase) : BaseViewModel() {
    val datas = MutableLiveData<List<BasketListItem>>()

    var name = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var postNum = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var extendAddress = MutableLiveData<String>()

    var totalPrice = MutableLiveData<Int>()

    init {
        name.value = "정재훈"
        phone.value = "01088335697"
//        postNum.value = "08763"
//        address.value = "서울특별시 관악구 남부순환로165길 59 (신림동, 미래)"
        extendAddress.value = "204호"
    }

    private fun isValidation() =
        !name.value.isNullOrEmpty() && !phone.value.isNullOrEmpty()
                && !postNum.value.isNullOrEmpty() && !address.value.isNullOrEmpty()
                && !extendAddress.value.isNullOrEmpty()

    fun requestPayment(onError:()->Unit) {
        if(isValidation()){
//            postPaymentUseCase.invoke()
        }else{
            onError()
        }
    }

    fun setImage(imageView: ImageView, path: String) {
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            imageView.clipToOutline = true
            Glide.with(imageView).load(bitmap).centerCrop().into(imageView)
        }
    }
}