package com.oldee.user.network.response

data class VersionInfoResponse(
    val data:VersionInfoData,
    override var errorMessage: String?,
    override var errorCode: String?,
    override var count: Int?,
    override var status: Int?,
    override var message: String?
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
