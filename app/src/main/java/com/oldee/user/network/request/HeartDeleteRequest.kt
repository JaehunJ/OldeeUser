package com.oldee.user.network.request

data class HeartDeleteRequest(
    val userId:String,
    val heartList:List<HeartDeleteList>
)

data class HeartDeleteList(
    val mainCategoryCode:String
)
