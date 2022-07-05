package com.oldee.user.network.response

data class SignUpResponse(
    val data: String?,
    override var errorMessage: String?,
    override var errorCode: String?,
    override var count: Int?,
    override var status: Int?,
    override var message: String?
) :
    BaseResponse()
