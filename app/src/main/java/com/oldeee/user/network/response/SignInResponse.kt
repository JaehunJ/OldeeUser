package com.oldeee.user.network.response

data class SignInResponse(
    val data: SignInResponseData,
    override var errorMessage: String?,
    override var errorCode: String?,
    override var count: Int?,
    override var status: Int?,
    override var message: String?
) : BaseResponse()

data class SignInResponseData(
    val userPolicyYn: Int,
    val userPhone: String,
    val userAlertYn: Int,
    val userEmail: String,
    val accessToken: String,
    val refreshToken: String,
    val userName: String,
    val userMarketingYn: Int
)