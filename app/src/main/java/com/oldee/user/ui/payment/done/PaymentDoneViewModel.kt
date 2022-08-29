package com.oldee.user.ui.payment.done

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.oldee.user.base.BaseViewModel
import com.oldee.user.custom.dpToPx
import com.oldee.user.network.response.PaymentListResponse
import com.oldee.user.usercase.GetPaymentDetailUseCase
import com.oldee.user.usercase.SetImageUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentDoneViewModel @Inject constructor(
    private val getOrderLogDetailUseCase: GetPaymentDetailUseCase,
    private val setImageUseCase: SetImageUseCase
):BaseViewModel(){
    val orderId = MutableLiveData<Int>()

    val res = MutableLiveData<PaymentListResponse>()

    fun requestOrder(id:Int){
        remote {
            val result = getOrderLogDetailUseCase.invoke(id)

            result?.let{
                Logger.e("requestOrder->success order")
                res.postValue(it)
            }
        }
    }

    fun setImage(imageView: ImageView, path:String){
        remote(false) {
            setImageUseCase.invoke(imageView.context, imageView, path, dpToPx(imageView.context, 8f).toInt())
        }
    }
}