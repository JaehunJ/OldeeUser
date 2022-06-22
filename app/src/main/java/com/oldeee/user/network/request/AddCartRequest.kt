package com.oldeee.user.network.request

data class AddCartRequest(
    val surveyList:List<AddCartRequestData>
)

data class AddCartRequestData(
    val code: String,
    val reformItemId: Int,
    val contents: String,
    val imageNameList: List<AddCartRequestImage>
)

data class AddCartRequestImage(
    val imageName:String
)