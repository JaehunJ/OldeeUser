package com.oldee.user.ui.payment.done

import androidx.lifecycle.MutableLiveData
import com.oldee.user.base.BaseViewModel
import com.oldee.user.usercase.GetPaymentDetailUseCase
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentDoneViewModel @Inject constructor(
    private val getOrderLogDetailUseCase: GetPaymentDetailUseCase
):BaseViewModel(){
    val orderId = MutableLiveData<Int>()

    fun requestOrder(id:Int){
        remote {
            val result = getOrderLogDetailUseCase.invoke(id)

            result?.let{
                Logger.e("success order")
            }
        }
    }
}