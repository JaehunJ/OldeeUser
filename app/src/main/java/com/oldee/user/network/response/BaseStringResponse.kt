package com.oldee.user.network.response

data class BaseStringResponse(
    val data:String,
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse()