package com.oldeee.user.network.request

data class AddShippingAddressRequest(
    val postalCode:String,
    val shippingAddress:String,
    val shippingAddressDetail:String,
    val userPhone:String,
    val shippingName:String,
    val shippingLastYn:Int
)
