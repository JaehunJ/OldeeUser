package com.oldeee.user.network.response

data class NoticeResponse (
    val data : List<NoticeResponseData>,
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse(){

}

data class NoticeResponseData(
    val contents:String,
    val title:String,
    val creationDate:String,
    val noticeId:Int
)