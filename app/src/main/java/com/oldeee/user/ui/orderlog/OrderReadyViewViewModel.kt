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

    var limit = 10
    var page = 0

    val resSize:Int
    get() {
        return res.value?.size?:0
    }

    fun requestPaymentList(limit:Int? = null, page:Int? = null) {
        remote {
            this.page = page?:0
            val result = getPaymentListUseCase.invoke(OrderLogFragment.READY, limit, page)

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