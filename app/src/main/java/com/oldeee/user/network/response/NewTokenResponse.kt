package com.oldeee.user.network.response

data class NewTokenResponse (
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse()

data class NewTokenData(
    val newAccessToken:String,
    val newRefreshToken:String,
    val refreshToken:String
)