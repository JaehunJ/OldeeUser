package com.oldee.user.network.request

data class PaymentPageRequest(
    val addressId:Int,
    val basketList:List<PaymentPageRequestItem>,
    val orderPrice:Int,
    val shippingFee:Int,
    val totalPrice:Int
)

data class PaymentPageRequestItem(
    val basketId:Int,
    val orderDetailTitle:String,
    val surveySeq:Int
)