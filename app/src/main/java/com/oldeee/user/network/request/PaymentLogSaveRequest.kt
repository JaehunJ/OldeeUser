package com.oldeee.user.network.request

data class PaymentLogSaveRequest(
    val addressId:Int,
    val basketList:List<PaymentLogBasketItem>,
    val orderPrice:Int,
    val shippingFee:Int,
    val totalPrice:Int
)

data class PaymentLogBasketItem(
    val basketId:Int,
    val orderDetailTitle:String,
    val surveySeq:Int
)
