package com.oldee.user.network.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class BasketListResponse(
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    val data: List<BasketListItem>,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse()

@Parcelize
data class BasketListItem(
    val basketId: Int,
    val classCode: String,
    val surveyId: String,
    val imageName: String,
    val reformPrice: String,
    val reformCode: String,
    val reformName: String,
    val itemName: String,
    val surveySeq: Int,
    val contents: String,
    val userUUId: Int,
    val expertUUId: String
) : Parcelable{
    fun getImageNameList() = imageName.split(',')
}
