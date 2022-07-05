package com.oldee.user.network.response

data class ImageResponse(
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?,
    val data: List<ImageItem>
) : BaseResponse()

data class ImageItem(
    val imageName: String
)
