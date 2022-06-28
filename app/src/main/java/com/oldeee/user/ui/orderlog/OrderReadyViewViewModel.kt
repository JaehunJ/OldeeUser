package com.oldeee.user.ui.orderlog

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.PaymentListItem
import com.oldeee.user.usercase.GetImageUseCase
import com.oldeee.user.usercase.GetPaymentListUseCase
import com.oldeee.user.usercase.SetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderReadyViewViewModel @Inject constructor(
    private val getPaymentListUseCase: GetPaymentListUseCase,
    private val setImageUseCase: SetImageUseCase
) : BaseViewModel() {
    val res = MutableLiveData<List<PaymentListItem>>()

    fun requestPaymentList() {
        remote {
            val result = getPaymentListUseCase.invoke(OrderLogFragment.READY)

            result?.let {
                res.postValue(it.data)
            }
        }
    }

    fun setImage(iv:ImageView, path:String){
        remote(false) {
            setImageUseCase.invoke(iv.context, iv, path)
        }
    }
}