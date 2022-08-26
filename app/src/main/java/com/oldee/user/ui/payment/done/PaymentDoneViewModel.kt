package com.oldee.user.ui.payment.done

import androidx.lifecycle.MutableLiveData
import com.oldee.user.base.BaseViewModel
import com.oldee.user.network.response.PaymentListResponse
import com.oldee.user.usercase.GetPaymentDetailUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentDoneViewModel @Inject constructor(
    private val getOrderLogDetailUseCase: GetPaymentDetailUseCase
):BaseViewModel(){
    val orderId = MutableLiveData<Int>()

    val res = MutableLiveData<PaymentListResponse>()

    fun requestOrder(id:Int){
        remote {
            val result = getOrderLogDetailUseCase.invoke(id)

            result?.let{
                Logger.e("success order")
                res.postValue(it)
            }
        }
    }
}