package com.oldeee.user.network.request

import com.oldeee.user.network.response.ImageItem

data class SaveReviewLogRequest(
    val orderDetailId:Int,
    val contents:String,
    val classCode:String,
    val mainCode:String,
    val designIcon:Int,
    val alterIcon:Int,
    val exprIcon:Int,
    val imageNameList:List<ImageItem>
)

//data class ImageItem(
//    val imageName:String
//)
