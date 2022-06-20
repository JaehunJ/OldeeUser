package com.oldeee.user.network.request

data class NewTokenRequest(
    val accessToken:String,
    val refreshToken:String
)