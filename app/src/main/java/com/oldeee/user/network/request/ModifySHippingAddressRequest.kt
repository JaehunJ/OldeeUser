package com.oldeee.user.network.request

data class ModifySHippingAddressRequest(
    val addressId:Int,
    val postalCode:String,
    val shippingAddress:String,
    val shippingAddressDetail:String,
    val userPhone:String,
    val shippingName:String
)
