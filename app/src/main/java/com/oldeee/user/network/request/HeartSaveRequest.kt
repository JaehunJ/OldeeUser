package com.oldeee.user.network.request

data class HeartSaveRequest(
    val userId:Int,
    val surveyList:List<HeartSaveList>
)

data class HeartSaveList(
    val categoryCode:String,
    val itemValue:String
)