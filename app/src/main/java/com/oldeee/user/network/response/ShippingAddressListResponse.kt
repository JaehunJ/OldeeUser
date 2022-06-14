package com.oldeee.user.network.response

data class ShippingAddressListResponse(
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?,
    val data:List<ShippingAddressListItem>
):BaseResponse()

data class ShippingAddressListItem(
    val shippingLastYn:Boolean,
    val shippingName:String,
    val postalCode:String,
    val userPhone:String,
    val shippingAddress:String,
    val userUUId:Int,
    val addressId:Int
)
