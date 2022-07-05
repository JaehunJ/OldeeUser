package com.oldee.user.network.request

data class PaymentRequest(
    val addressId:Int,
    val basketList:List<PaymentBasketItem>,
    val orderPrice:Int,
    val shippingFee:Int,
    val totalPrice:Int
)

data class PaymentBasketItem(
    val basketId:Int,
    val orderDetailTitle:String,
    val surveySeq:Int
)
