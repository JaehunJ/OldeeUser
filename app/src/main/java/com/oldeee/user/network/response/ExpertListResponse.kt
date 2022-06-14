package com.oldeee.user.network.response

data class ExpertListResponse(
    val data:List<ExpertListItem>,
    override var errorMessage: String?,
    override var errorCode: String?,
    override var count: Int?,
    override var status: Int?,
    override var message: String?
):BaseResponse()

data class ExpertListItem(
    val expertName:String,
    val expertEmail:String,
    val expertPrifileImg:String,
    val expertStoreName:String,
    val expertPhone:String,
    val userId:String
)
