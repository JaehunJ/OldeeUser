package com.oldeee.user.network.response

data class VersionInfoResponse(
    val count: Int,
    val status:Int,
    val message:String,
    val data:VersionInfoData,
    override var errorMessage: String?,
    override var errorCode: String?
) : BaseResponse()

data class VersionInfoData(
    val app_type:String,
    val app_status:String,
    val contents:String,
    val os_type:String,
    val version_code:String,
    val version_id:Int,
    val creation_date:String,
    val title:String
)
