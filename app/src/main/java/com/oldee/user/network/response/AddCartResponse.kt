package com.oldee.user.network.response

data class AddCartResponse(
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?,
    val data:AddCartResponseData
) :BaseResponse()

data class AddCartResponseData(
    val surveySeq:Int
)