package com.oldeee.user.network.response

data class DesignDetailResponse(
    override var count:Int?,
    override var status:Int?,
    override var message:String?,
    val data:List<DesignDetailData>,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse()

data class DesignDetailData(
    val expertName:String,
    val imageName:String,
    val heartCheck:Int,
    val beforeImageName:String,
    val reformId:Int,
    val afterImageName:String,
    val minDay:Int,
    val reformItemName:String,
    val iconImageId:List<String>,
    val reformItemId:Int,
    val contents:String,
    val maxDay:Int,
    val expertUUId:Int
)
