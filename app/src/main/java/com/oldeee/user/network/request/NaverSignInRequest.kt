package com.oldeee.user.network.request

data class NaverSignInRequest(
    val accessToken:String,
    val refreshToken:String,
    val expireAt:String,
    val userEmail:String,
    val userSnsId:String
)