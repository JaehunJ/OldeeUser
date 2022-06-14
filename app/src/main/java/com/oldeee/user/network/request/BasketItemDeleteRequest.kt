package com.oldeee.user.network.request

data class BasketItemDeleteRequest(
    val selectList:List<BasketItemDeleteData>
)

data class BasketItemDeleteData(
    val basketId:Int
)