package com.oldee.user.network.response

data class FooterResponse(
    val data:FooterData,
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?
):BaseResponse(){
    data class FooterData(
        val commNtslDclr:String,
        val eml:String,
        val rprsv:String,
        val addr:String,
        val telNo:String,
        val brNo:String
    )
}
