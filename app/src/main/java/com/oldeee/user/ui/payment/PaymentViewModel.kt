package com.oldeee.user.ui.payment

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.request.AddShippingAddressRequest
import com.oldeee.user.network.request.PaymentBasketItem
import com.oldeee.user.network.request.PaymentRequest
import com.oldeee.user.network.response.BasketListItem
import com.oldeee.user.network.response.ShippingAddressListItem
import com.oldeee.user.usercase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    val getImageUseCase: GetImageUseCase,
    val postPaymentUseCase: PostPaymentUseCase,
    val postAddressUseCase: PostAddressUseCase,
    val getAddressListUseCase: GetAddressListUseCase,
    val getAddressByIdUserCase: GetAddressByIdUserCase
) : BaseViewModel() {
    val datas = MutableLiveData<List<BasketListItem>>()

    var name = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var postNum = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var extendAddress = MutableLiveData<String>()

    var totalPrice = MutableLiveData<Int>()

    //res data
    val latestAddress = MutableLiveData<ShippingAddressListItem?>()

    val id: Int?
        get() = latestAddress.value?.addressId

    init {
//        name.value = "정재훈"
//        phone.value = "01088335697"
//        postNum.value = "08763"
//        address.value = "서울특별시 관악구 남부순환로165길 59 (신림동, 미래)"
//        extendAddress.value = "204호"
    }

    fun isValidation() =
        !name.value.isNullOrEmpty() && !phone.value.isNullOrEmpty()
                && !postNum.value.isNullOrEmpty() && !address.value.isNullOrEmpty()
                && !extendAddress.value.isNullOrEmpty()

    fun requestAddressList() {
        remote {
            val result = getAddressListUseCase.invoke()

            result?.let {
                val data = it.data

                if (data.isNotEmpty()) {
                    latestAddress.postValue(data[0])
                } else {
                    latestAddress.postValue(null)
                }
            }
        }
    }

    suspend fun requestGetAddressById(id: Int): Boolean {
        val result = getAddressByIdUserCase.invoke(id)

        result?.let {
            val items = it.data

            return items.isNotEmpty()
        }

        return false
    }

    suspend fun isAddressModified(
        oldAdd: String,
        oldAddDetail: String,
        newAdd: String,
        newAddDetail: String
    ): Boolean {
        return oldAdd != newAdd || oldAddDetail != newAddDetail
    }

    fun requestPaymentProcess(onComplete:()->Unit, onError: () -> Unit) {
        viewModelScope.launch {
            if (isValidation()) {
                //주소 먼저 등록할지 안할지
                val addressId = getPaymentAddressId()
                val paymentData = getPaymentData(addressId?:0)

                val result = postPaymentUseCase.invoke(paymentData)

                result?.let{
                    if(it.data == "success"){
                        onComplete()
                    }
                }
            } else {
                onError.invoke()
            }
        }
    }

    suspend fun getPaymentAddressId(): Int? {
        //본래 쓰던 주소가 존재하면
        if (latestAddress.value != null) {
            val oldData = latestAddress.value
            val isPostingNewAddress = isAddressModified(
                oldAdd = oldData!!.shippingAddress,
                oldAddDetail = oldData.shippingAddressDetail,
                newAdd = address.value ?: "",
                newAddDetail = extendAddress.value ?: ""
            )

            if (isPostingNewAddress) { //새로 주소를 등록해야함
                val newId = requestPostAddress()

                return if (newId == -1) {
                    null
                } else {
                    newId
                }
            } else {
                return latestAddress.value?.addressId
            }
        }else{//아니면 id 발급
            val newId = requestPostAddress()

            return if (newId == -1) {
                null
            } else {
                newId
            }
        }

        return null
    }

    suspend fun requestPostAddress(): Int {
        val requestData = AddShippingAddressRequest(
            postalCode = postNum.value ?: "",
            shippingAddress = "",
            shippingAddressDetail = "",
            userPhone = "",
            shippingName = "",
            shippingLastYn = 0
        )

        val result = postAddressUseCase.invoke(requestData)

        result?.let {
            return it.data.addressId
        }

        return -1
    }

    suspend fun getPaymentData(addressId: Int): PaymentRequest {
        var list = mutableListOf<PaymentBasketItem>()

        val oldList = datas.value

        oldList?.let {
            it.forEach { item ->
                list.add(
                    PaymentBasketItem(
                        basketId = item.basketId,
                        orderDetailTitle = item.reformName,
                        surveySeq = item.surveySeq
                    )
                )
            }
        }

        return PaymentRequest(
            addressId = addressId,
            basketList = list,
            orderPrice = this.totalPrice.value?:0,
            shippingFee = 0,
            totalPrice = this.totalPrice.value?:0,
        )
    }

//    suspend fun requestPayment(addressId:Int, onError: () -> Unit) {
//        val result = postPaymentUseCase.invoke()
//    }

    fun setImage(imageView: ImageView, path: String) {
        remote(false) {
            val bitmap = getImageUseCase.invoke(path)
            imageView.clipToOutline = true
            Glide.with(imageView).load(bitmap).centerCrop().into(imageView)
        }
    }
}