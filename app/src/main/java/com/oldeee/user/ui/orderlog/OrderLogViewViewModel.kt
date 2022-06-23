package com.oldeee.user.ui.orderlog

import androidx.lifecycle.MutableLiveData
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.PaymentListItem
import com.oldeee.user.network.response.PaymentListResponse
import com.oldeee.user.usercase.GetPaymentListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderLogViewViewModel @Inject constructor(private val getPaymentListUseCase: GetPaymentListUseCase) : BaseViewModel() {

    val res = MutableLiveData<List<PaymentListItem>>()

    fun requestPaymentList(){
        remote {
            val result = getPaymentListUseCase.invoke(1)

            result?.let{
                res.postValue(it.data)
            }
        }
    }
}