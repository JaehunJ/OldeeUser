package com.oldee.user.network.request

data class SignInRequest(
    val accessToken:String,
    val refreshToken:String,
    val expireAt:String,
    val userEmail:String,
    val userSnsId:String
)
