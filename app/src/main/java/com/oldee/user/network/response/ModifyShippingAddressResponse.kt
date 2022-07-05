package com.oldee.user.network.response

data class ModifyShippingAddressResponse(
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?,
    val data:ModifyShippingAddressItem
):BaseResponse()

data class ModifyShippingAddressItem(
    val addressId:Int
)