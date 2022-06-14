package com.oldeee.user.network.response

data class ReviewListResponse(
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?,
    val data:List<ReviewListItem>
):BaseResponse()

data class ReviewListItem(
    val classCode:String,
    val desingIcon:Int,
    val exprIcon:Int,
    val contents:String,
    val imagePath:String,
    val modifiedDate:String,
    val userName:String,
    val expertUUId:Int,
    val creationDate:String,
    val alterIcon:Int,
    val orderDetailId:Int
)
