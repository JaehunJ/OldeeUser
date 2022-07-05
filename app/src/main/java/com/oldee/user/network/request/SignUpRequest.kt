package com.oldee.user.network.request

data class SignUpRequest(
    val userName:String,
    val userEmail: String,
    val userAlertYn:Int,
    val userPhone:String,
    val userMarketingYn:Int,
    val userPolicyYn:Int,
    val userOsType:String,
    val userSnsType:String,
    val userSnsId:String
)
