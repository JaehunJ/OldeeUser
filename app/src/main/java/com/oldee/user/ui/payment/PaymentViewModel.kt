package com.oldee.user.ui.payment

import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.oldee.user.base.BaseViewModel
import com.oldee.user.data.view.PaymentDoneViewData
import com.oldee.user.network.request.*
import com.oldee.user.network.response.BasketListItem
import com.oldee.user.network.response.ShippingAddressListItem
import com.oldee.user.usercase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    val getImageUseCase: GetImageUseCase,
    val postPaymentUseCase: PostPaymentUseCase,
    val postAddressUseCase: PostAddressUseCase,
    val getAddressListUseCase: GetAddressListUseCase,
    val getAddressByIdUserCase: GetAddressByIdUserCase,
    val getPaymentPageUseCase: GetPaymentPageUseCase
) : BaseViewModel() {
    companion object {
        val ADDRESS_ALL = 0
        val ADDRESS_PREV = 1
    }

    val datas = MutableLiveData<List<BasketListItem>>()

    var name = MutableLiveData<String>()
    var phone = MutableLiveData<String>()
    var postNum = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var extendAddress = MutableLiveData<String>()

    var totalPrice = MutableLiveData<Int>()

    //res data
    val latestAddress = MutableLiveData<ShippingAddressListItem?>()
    val allAddress = MutableLiveData<List<ShippingAddressListItem?>>()

    val id: Int?
        get() = latestAddress.value?.addressId

    init {

    }

    fun getPaymentDoneViewDataList():List<PaymentDoneViewData>{
        val list = mutableListOf<PaymentDoneViewData>()

        datas.value?.let{
            it.forEach {
//                list.add(PaymentDoneViewData(
//                    it.imageName?:"", it.
//                ))
            }
        }

        return list
    }

    fun isValidation() =
        !name.value.isNullOrEmpty() && !phone.value.isNullOrEmpty()
                && !postNum.value.isNullOrEmpty() && !address.value.isNullOrEmpty()
                && !extendAddress.value.isNullOrEmpty()


    fun requestPaymentPage(onNext:(String)->Unit) {
        viewModelScope.launch {
            datas.value?.let {
                if(it.isNotEmpty()){
                    val itemList = mutableListOf<PaymentPageRequestItem>()

                    it.forEach { item->
                        itemList.add(PaymentPageRequestItem(item.basketId, item.reformName, item.surveySeq))
                    }

                    val data = PaymentPageRequest(
                        itemList.toList(),
                        totalPrice.value?:0,
                        3000,
                        totalPrice.value?:0
                    )
                    val html = getPaymentPageUseCase.invoke(data)

                    if(!html.isNullOrEmpty()){
                        onNext.invoke(html)
                    }
                }
            }

        }
    }

    fun requestAddressLatest() {
        remote {
            val result = getAddressListUseCase.invoke(ADDRESS_PREV)

            result?.let {
                val data = it.data

                if (data.isNotEmpty()) {
                    allAddress.postValue(data)
                    latestAddress.postValue(data[0])
                } else {
                    latestAddress.postValue(null)
                }
            }
        }
    }

    fun requestAddressList() {
        remote {
            val result = getAddressListUseCase.invoke(ADDRESS_ALL)

            result?.let {
                val data = it.data

                allAddress.postValue(data)
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
        oldName: String,
        newName: String,
        oldPhone: String,
        newPhone: String,
        oldAdd: String,
        oldAddDetail: String,
        newAdd: String,
        newAddDetail: String
    ): Boolean {
        return oldName != newName || oldPhone != newPhone || oldAdd != newAdd || oldAddDetail != newAddDetail
    }

    fun requestPaymentProcess(onComplete: () -> Unit, onError: (String) -> Unit) {
        remote {
            if (isValidation()) {
                //주소 먼저 등록할지 안할지
                val addressId = getPaymentAddressId()

                if (addressId == null) {
                    onError("주소 등록중 오류가 발생했습니다.")
                }

                val paymentData = getPaymentData(addressId ?: 0)

                val result = postPaymentUseCase.invoke(paymentData)

                result?.let {
                    if (it.data == "success") {
                        onComplete()
                    }
                }
            } else {
                onError.invoke("누락된 정보가 있습니다.")
            }
        }
    }

    suspend fun getPaymentAddressId(): Int? {
        //본래 쓰던 주소가 존재하면
        if (latestAddress.value != null) {
            val oldData = latestAddress.value
            val isPostingNewAddress = isAddressModified(
                oldName = oldData?.shippingName ?: "",
                newName = name.value ?: "",
                oldPhone = oldData?.userPhone ?: "",
                newPhone = phone.value ?: "",
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
        } else {//아니면 id 발급
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
            shippingAddress = address.value ?: "",
            shippingAddressDetail = extendAddress.value ?: "",
            userPhone = phone.value ?: "",
            shippingName = name.value ?: "",
            shippingLastYn = 1
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
            orderPrice = this.totalPrice.value ?: 0,
            shippingFee = 0,
            totalPrice = this.totalPrice.value ?: 0,
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