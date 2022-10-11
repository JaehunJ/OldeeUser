package com.oldee.user.network.response

data class DesignListResponse(

    val data: List<DesignListItem>,
    override var errorMessage: String?,
    override var errorCode: String?,
    override var count: Int?,
    override var status: Int?,
    override var message: String?
) : BaseResponse()

/**
 * TODO 프로필 이미지 내려와야댐
 */
data class DesignListItem(
    val mainImageName: String?,
    val expertName: String,
    val heartCheck: Int,
    val beforeImageName: String,
    val contents: String,
    val price: Int,
    val reformId: Int,
    val afterImageName: String,
    val storeName: String,
    val expertUUId: Int,
    val reformName: String,
    val heartId: Any?
)