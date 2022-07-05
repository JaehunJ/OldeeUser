package com.oldee.user.network.response

data class BookMarkListResponse(
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?,
    val data:List<BookMarkListItem>
):BaseResponse()

data class BookMarkListItem(
    val main_image_name:String,
    val contents:String,
    val price:Int,
    val reformId:Int,
    val reformName:String
)