package com.oldeee.user.network.response

data class DesignDetailResponse(
    override var count:Int?,
    override var status:Int?,
    override var message:String?,
    val data:DesignDetailData,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse()

data class DesignDetailData(
    val expertName:String,
    val imageName:String,
    val heartCheck:Int,
    val beforeImageName:String,
    val itemCode:String,
    val reformId:Int,
    val afterImageName:String,
    val reformCode:String,
    val reformName:String,
    val heartId:Int,
    val minDay:Int,
    val reformItemName:String,
    val iconImageId:String,
    val reformItemId:String,
    val contents:String,
    val price:Int,
    val maxDay:Int,
    val expertUUId:Int,
    val profileImg:String
){
    fun getImageNameList() = imageName.split(',')

    fun getReformItemNameList() = reformItemName.split(',')

    fun getIconImageIdList() = iconImageId.split(',')

    fun getReformItemIdList() = reformItemId.split(',')
}
