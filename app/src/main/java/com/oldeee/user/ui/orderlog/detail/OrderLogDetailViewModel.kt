package com.oldeee.user.ui.orderlog.detail

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.response.PaymentListItem
import com.oldeee.user.usercase.GetPaymentDetailUseCase
import com.oldeee.user.usercase.SetImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderLogDetailViewModel @Inject constructor(
    private val setImageUseCase: SetImageUseCase,
    private val getPaymentDetailUseCase: GetPaymentDetailUseCase
) : BaseViewModel() {


    val res = MutableLiveData<PaymentListItem>()

    fun setImage(imageView: ImageView, path: String) {
        remote(false) {
            setImageUseCase.invoke(imageView.context, imageView, path)
        }
    }

    fun requestPaymentDetail(orderId: Int) {
        remote(false) {
            val result = getPaymentDetailUseCase.invoke(orderId)
            result?.let{
                if(it.data.isNotEmpty()) {
                    res.postValue(it.data.first())
                }
            }
        }
    }
}